/**
  * author:   Ronny Fuchs, info@asderix.com
  * license:  Apache license 2.0 - https://www.apache.org/licenses/
  */

package esc.index

import esc.configuration._
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index._
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.search._
import java.io.File
import java.io.IOException
import java.nio.file._

/**
  * The IndexFactory opens and/or creates an index.
  */
object IndexFactory {    

    /**
      * Creates a new index. Returns an exception if such a folder already exists.
      *
      * @param  indexBase  The base folder for the index.
      * @param  indexName  The name of the index.
      * @param  similarityConfig  Optional. A SimilarityConfiguration.
      *
      * @return Returns a configurated Indexer object.
      */
    @throws(classOf[IOException])
    def createIndex(indexBase : String, indexName : String, similarityConfig : SimilarityConfig = new SimilarityConfig()) : Indexer = {        
        new File(indexBase + indexName).mkdirs() match {
            case (true) => {
                val indexConfig = new IndexWriterConfig(new StandardAnalyzer())
                indexConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE)
                openIndex(indexBase + indexName, similarityConfig, indexConfig) 
            }
            case (false) => throw new IOException
        }        
    }

    /**
      * Opens an existing index.
      * 
      * @param  indexBase  The base folder for the index.
      * @param  indexName  The name of the index.
      * @param  similarityConfig  Optional. A SimilarityConfiguration.
      *
      * @return Returns a configurated Indexer object.
      */
    def openIndex(indexBase : String, indexName : String, similarityConfig : SimilarityConfig = new SimilarityConfig()) : Indexer = {                
        val indexConfig = new IndexWriterConfig(new StandardAnalyzer())
        indexConfig.setOpenMode(IndexWriterConfig.OpenMode.APPEND)
        openIndex(indexBase + indexName, similarityConfig, indexConfig)                
    }

    /**
      * Opens an existing index, or creates the index if it does not already exist.
      *
      * @param  indexBase  The base folder for the index.
      * @param  indexName  The name of the index.
      * @param  similarityConfig  Optional. A SimilarityConfiguration.
      *
      * @return Returns a configurated Indexer object.
      */
    def openOrCreateIndex(indexBase : String, indexName : String, similarityConfig : SimilarityConfig = new SimilarityConfig()) : Indexer = {
        new File(indexBase + indexName).mkdirs()
        val indexConfig = new IndexWriterConfig(new StandardAnalyzer())
        indexConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND)
        openIndex(indexBase + indexName, similarityConfig, indexConfig)
    }

    /**
      * Creates a Finder.
      *
      * @param  indexBase  The base folder for the index.
      * @param  indexName  The name of the index.
      * @param  similarityConfig  Optional. A SimilarityConfiguration.
      *
      * @return Returns a configurated Finder object.
      */
    def openIndexForSearch(indexBase : String, indexName : String, similarityConfig : SimilarityConfig = new SimilarityConfig()) : Finder = {      
      new Finder(new IndexSearcher(
        DirectoryReader.open(FSDirectory.open(
            new File(indexBase + indexName).toPath())
          )
        ),
        similarityConfig
      )
    }

    // ---
    private def openIndex(fullPath : String, similarityConfig : SimilarityConfig, indexConfig : IndexWriterConfig) : Indexer = {        
        val indexDirectory = FSDirectory.open(new File(fullPath).toPath())          
        val indexWriter = new IndexWriter(indexDirectory, indexConfig)
        new Indexer(indexWriter, similarityConfig)
    }
}
