/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.ai

import de.kherud.llama._
import java.nio.file.Paths
import esc.configuration._

trait LMRunner:
    def aiConfig: AiConfig
    def changeAiConfig(newAiConfig: AiConfig): Unit
    def loadModel(path: String): Unit
    def prompt(prompt: String, maxTokens: Option[Int] = None): String

/**
* Default local LMRunner. Need a llama.cpp compatible LLM model
* like GGUF.
*
*/
object LocalLMRunner extends LMRunner:
    private var isLoaded: Boolean = false
    private var lmModel: Option[LlamaModel] = None

    private var modelPath: String = ""
    private var _aiConfig: AiConfig = new AiConfig()
    
    override def aiConfig: AiConfig = _aiConfig

    /**
    * Set a new AiConfig to the object.
    * Automatically reload the model if the model is already loaded
    * and the model path ist still set.
    *
    */
    override def changeAiConfig(newAiConfig: AiConfig): Unit =
        _aiConfig = newAiConfig
        if isLoaded && modelPath.nonEmpty then
            loadModel(modelPath)

    /**
    * Load the model by the given path.
    * Remarks: Only llama-cpp formats are supported.
    * Most likely models in the GGUF format.
    *
    */
    override def loadModel(path: String): Unit = synchronized:
        close()
        modelPath = path
        val p = ModelParameters()
        p.setModel(Paths.get(path).toString)
        p.setCtxSize(aiConfig.modelContextSize)
        p.setBatchSize(aiConfig.modelBatchSize)
        p.setGpuLayers(aiConfig.modelGpuLayers)    
        p.setThreads(aiConfig.modelThreads)    
        p.setThreadsBatch(aiConfig.modelThreads)

        val m = new LlamaModel(p)
        lmModel = Some(m)
        isLoaded = true

    /**
    * Execute the given text prompt with the LLM model.
    * 
    * @param maxTokens
    *  Default is None and the value from the AiConfig is used.
    */
    override def prompt(prompt: String, maxTokens: Option[Int] = None): String = synchronized:
        require(isLoaded && lmModel.isDefined, "Model not loaded. First load the model via loadModel(path)")
        val inferParams = createInferenceParams(prompt, maxTokens)
        val result = lmModel.get.complete(inferParams)
        result

    // --
    private def createInferenceParams(prompt: String, maxTokens: Option[Int] = None): InferenceParameters =
        val p = new InferenceParameters(prompt)
        p.setTemperature(aiConfig.inferenceTemperature)
        p.setTopK(aiConfig.inferenceTopK)
        p.setTopP(aiConfig.inferenceTopP)
        p.setMinP(aiConfig.inferenceMinP)
        p.setRepeatPenalty(aiConfig.inferenceRepeatPenalty)
        p.setNPredict(maxTokens.getOrElse(aiConfig.inferenceMaxTokens))
        p.setPresencePenalty(aiConfig.inferencePresencePenalty)
        p.setFrequencyPenalty(aiConfig.inferenceFrequencyPenalty)
        p.setStopStrings(aiConfig.inferenceStopList*)
        p

    // --
    private def close(): Unit = synchronized:
        lmModel.foreach(_.close())
        lmModel = None
        isLoaded = false
        
/**
* Management service for the LLM runner instances. Use this
* service to change the LLM runner if needed.
* The AiAgent object uses this service for LLM inference calls.
*
*/
object LMRunnerService extends LMRunner:
    @volatile private var lmRunner: LMRunner = LocalLMRunner

    def setLMRunner(newLMRunner: LMRunner): Unit = synchronized:
        lmRunner = newLMRunner

    override def aiConfig: AiConfig =
        lmRunner.aiConfig

    override def changeAiConfig(newAiConfig: AiConfig): Unit =
        lmRunner.changeAiConfig(newAiConfig)

    override def loadModel(path: String): Unit = synchronized:
        lmRunner.loadModel(path)

    override def prompt(prompt: String, maxTokens: Option[Int] = None): String = synchronized:
        lmRunner.prompt(prompt, maxTokens)
