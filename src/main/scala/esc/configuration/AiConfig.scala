/** @author:
  *   Ronny Fuchs, info@asderix.com
  * @license:
  *   Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.configuration

/**
  * Configuration class for the local LLM (llama.cpp via llama-cpp-java).
  *
  * All parameters are preconfigured for deterministic behaviour, low randomness,
  * and minimal hallucinations. This is essential for entity and name-matching
  * use cases, where predictable and reproducible outputs are required.
  *
  * @param modelContextSize
  *   Size of the model’s context window in tokens. Determines how many tokens
  *   the model can keep in memory during inference.
  *   **Default:** `1024`
  *
  * @param modelBatchSize
  *   Number of tokens processed per batch during inference. Higher values may
  *   improve throughput but also increase memory consumption.
  *   **Default:** `32`
  *
  * @param modelGpuLayers
  *   Number of model layers to offload onto the GPU. Requires llama.cpp
  *   compiled with GPU support. Improves inference speed if GPU is available.
  *   Use `0` to run entirely on CPU.
  *   **Default:** `0`
  *
  * @param modelThreads
  *   Number of CPU threads used for inference. Defaults to the number of
  *   available CPU cores minus two, ensuring system responsiveness.
  *   **Default:** `Runtime.availableProcessors - 2` (minimum 1)
  *
  * @param inferenceTemperature
  *   Sampling temperature controlling randomness. Lower values produce more
  *   deterministic outputs; higher values allow more creativity.
  *   **Recommended:** `0.2 – 0.8`
  *   **Default:** `0.6`
  *
  * @param inferenceTopK
  *   Limits sampling to the top *K* highest-probability tokens. Lower values
  *   reduce randomness and help avoid hallucinations.
  *   **Default:** `5`
  *
  * @param inferenceTopP
  *   Nucleus sampling threshold. Model samples only from tokens whose
  *   cumulative probability reaches `top_p`. Combines well with `top_k`.
  *   **Default:** `1.0` (disabled)
  *
  * @param inferenceMinP
  *   Minimum probability threshold for token sampling. Prevents sampling from
  *   extremely low-probability tokens, improving determinism.
  *   **Default:** `0.1`
  *
  * @param inferenceRepeatPenalty
  *   Penalty factor applied to recently used tokens to reduce repetitive
  *   outputs. Values slightly above `1.0` discourage repetition.
  *   **Default:** `1.5`
  *
  * @param inferenceMaxTokens
  *   Maximum number of tokens the model may generate for a single inference
  *   request. Protects against runaway generation.
  *   **Default:** `256`
  *
  * @param inferencePresencePenalty
  *   Penalizes tokens that already appear in the output to increase topic
  *   diversity. Similar to OpenAI's presence penalty.
  *   **Default:** `0.2`
  *
  * @param inferenceFrequencyPenalty
  *   Penalizes tokens proportionally to how often they appear in the output,
  *   reducing repetitive patterns. Similar to OpenAI's frequency penalty.
  *   **Default:** `0.2`
  *
  * @param inferenceStopList
  *   Array of strings marking stop conditions. If the model generates any of
  *   these strings, inference stops immediately.
  *   **Default:** empty array
  *
  * @param agentSimilarityThresholdForHitToExplain
  *   Similarity threshold used by match-explanation logic. Determines whether
  *   two entities are considered sufficiently similar for detailed
  *   explanation or justification.
  *   **Default:** `0.8`
  */
case class AiConfig(
    modelContextSize: Int = 1024,
    modelBatchSize: Int = 32,
    modelGpuLayers: Int = 0,
    modelThreads: Int = math.max(1, Runtime.getRuntime.availableProcessors() - 2),
    inferenceTemperature: Float = 0.6f,
    inferenceTopK: Int = 5,
    inferenceTopP: Float = 1.0f,
    inferenceMinP: Float = 0.1f,
    inferenceRepeatPenalty: Float = 1.5,
    inferenceMaxTokens: Int = 256,
    inferencePresencePenalty: Float = 0.2,
    inferenceFrequencyPenalty: Float = 0.2,
    inferenceStopList: Array[String] = Array(),
    agentSimilarityThresholdForHitToExplain: Double = 0.8
)
