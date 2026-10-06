    _____ _____ _____                                                                                               
    |  ___/  ___/  __ \                                                                                              
    | |__ \ `--.| /  \/                                                                                              
    |  __| `--. \ |                                                                                                  
    | |___/\__/ / \__/\                                                                                              
    \____/\____/ \____/                                                                                              
                                                                                                                 
                                                                                                                 
     _____      _   _ _           _____ _           _ _            _ _           _____ _               _             
    |  ___|    | | (_) |         /  ___(_)         (_) |          (_) |         /  __ \ |             | |            
    | |__ _ __ | |_ _| |_ _   _  \ `--. _ _ __ ___  _| | __ _ _ __ _| |_ _   _  | /  \/ |__   ___  ___| | _____ _ __ 
    |  __| '_ \| __| | __| | | |  `--. \ | '_ ` _ \| | |/ _` | '__| | __| | | | | |   | '_ \ / _ \/ __| |/ / _ \ '__|
    | |__| | | | |_| | |_| |_| | /\__/ / | | | | | | | | (_| | |  | | |_| |_| | | \__/\ | | |  __/ (__|   <  __/ |   
    \____/_| |_|\__|_|\__|\__, | \____/|_|_| |_| |_|_|_|\__,_|_|  |_|\__|\__, |  \____/_| |_|\___|\___|_|\_\___|_|   
                           __/ |                                          __/ |                                      
                          |___/                                          |___/                                       

## Description
An AI powered powerful name matching library that is easy to use. Built on the shoulders of giants.

The solution supports various name-matching problems. Examples are Spelling mistakes, different spellings,
translation and transcription differences, hyphenation and merging, order of name elements, missing name
elements, known abbreviations, etc. These problems can all be solved by the solution.

In addition, the comparison of the secondary criteria country (country of birth, nationalities, countries of
domicile, etc.) and dates of birth/dates of founding is also supported.

The solution is optimized both for the comparison of natural persons and for the comparison of companies and
organizations.

Thanks to a LLM integration it's support good transliteration even for scripts like Chinese.

## Status
The library was available in a stable version. As of October 2026, after 8 years, this library will no longer be actively developed.

## Project website
The official project website is here: https://esc.asderix.com/ - Roadmap and release notes are on GitHub-Wiki.

## Usage
You find a start guide with examples on the project website: https://esc.asderix.com/start.html
Find the current version of the ScalaDoc here: https://esc.asderix.com/scaladoc/latest/

The unit test cases are also a good source for seeing how the library can be used. Almost all available options are applied there.

## Get the binary
Alternatively to the release (complete binary with all dependencies) on Github, the binary can also be downloaded on the project website: https://esc.asderix.com/download.html

## LLM usage
To use the LLM capabilities of this library you have two option:
- Use an API like Gemini
- Provide a local LLM model

Local LLM model: The library is tested with the following
open source model: Mistral-Small-3.2-24B-Instruct-2506-Q4_0.gguf
Supported are all models in a format which is supported by llama.cpp. Most likley the GGUF format.

Call one of the following methods before using LLM based functionalities:
- LMRunner.loadModel(path_to_the_model)
- AiAgent.loadModel(path_to_the_model)

For LLM API actually only a Gemini implementation is available. For other API please implement the LMRunner trait
with your own API client implementation.

## No need for LLM
An LLM model or an LLM API is not necessary to use this library. With the help of an LLM, you can better transliterate certain alphabets and explain hits in words etc. You do not need to provide an LLM model or API for the name matching itself, and the solution runs efficiently with few resources.

## Bugs & features
Please report bugs or missed features to: info@asderix.com.

## License:
Apache 2.0.

Please see at "Third party libraries" for licenses used by the included third party libraries.

## Third party libraries
Credits to the following third party libraries (license):
- Apache Lucene (Apache 2.0)
- ICU4J (Unicode-3.0)
- UPickle (MIT)
- java-llama.cpp (MIT)

See THIRD-PARTY-LICENSES.txt for more information.
