/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.ai

import de.kherud.llama._
import java.nio.file.Paths
import esc.configuration._

object LMRunner {
    private var isLoaded: Boolean = false
    private var lmModel: Option[LlamaModel] = None

    private var modelPath: String = ""
    private var _aiConfig: AiConfig = new AiConfig()
    
    def aiConfig: AiConfig = _aiConfig

    /**
    * Set a new AiConfig to the object.
    * Automatically reload the model if the model is already loaded
    * and the model path ist still set.
    *
    */
    def changeAiConfig(newAiConfig: AiConfig) = {
        _aiConfig = newAiConfig
        if (isLoaded && modelPath.nonEmpty) {
            loadModel(modelPath)
        }
    }

    /**
    * Load the model by the given path.
    * Remarks: Only llama-cpp formats are supported.
    * Most likely models in the GGUF format.
    *
    */
    def loadModel(path: String) = synchronized {
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
    }

    /**
    * Execute the given text prompt with the LLM model.
    * 
    * @param maxTokens
    *  Default is None and the value from the AiConfig is used.
    */
    def prompt(prompt: String, maxTokens: Option[Int] = None): String = synchronized {
        require(isLoaded && lmModel.isDefined, "Model not loaded. First load the model via loadModel(path)")
        val inferParams = createInferenceParams(prompt, maxTokens)
        val result = lmModel.get.complete(inferParams)
        result
    }

    // --
    private def createInferenceParams(prompt: String, maxTokens: Option[Int] = None): InferenceParameters = {
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
    }

    // --
    private def close(): Unit = synchronized {
        lmModel.foreach(_.close())
        lmModel = None
        isLoaded = false
    }
}