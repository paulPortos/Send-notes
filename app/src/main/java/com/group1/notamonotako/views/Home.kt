package com.group1.notamonotako.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.group1.notamonotako.R
import com.group1.notamonotako.external_api.dictionary.DictionaryClient
import com.group1.notamonotako.external_api.dictionary.DictionaryService
import com.group1.notamonotako.external_api.dictionary.requests_response.DictionaryRequest
import kotlinx.coroutines.launch

class Home : AppCompatActivity() {

    private lateinit var searchWord: EditText
    private lateinit var tvContents: TextView
    private lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchWord = findViewById(R.id.searchWord)
        tvContents = findViewById(R.id.tvContents)
        btnSearch = findViewById(R.id.btnSearch)

        this.btnSearch.setOnClickListener {
            Log.d("DictionaryAPI", "Btn Clicked")
            val word = this.searchWord.text.toString()
            fetchWordDefinition(word)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchWordDefinition(word: String) {
        lifecycleScope.launch {
            try {
                Log.d("DictionaryAPI", "Fetching definition for word: $word")
                // Make the API call to get the word definition
                val dictionaryService = DictionaryClient.retrofit.create(DictionaryService::class.java)
                val response = dictionaryService.getWordDefinition(word)

                if (response.isSuccessful) {
                    val definitions = response.body()
                    definitions?.let {
                        val wordInfo = StringBuilder()

                        // Add word and phonetic
                        wordInfo.append("Word: ${it[0].word}\n")
                        it[0].phonetic?.let { phonetic ->
                            wordInfo.append("Phonetic: $phonetic\n")
                        }

                        // Add origin if available
                        it[0].origin?.let { origin ->
                            wordInfo.append("Origin: $origin\n")
                        }

                        // Add meanings and definitions
                        it[0].meanings.forEach { meaning ->
                            wordInfo.append("\nPart of Speech: ${meaning.partOfSpeech}\n")
                            meaning.definitions.forEach { definition ->
                                wordInfo.append("Definition: ${definition.definition}\n")
                                definition.example?.let { example ->
                                    wordInfo.append("Example: $example\n")
                                }
                            }
                        }

                        // Set the concatenated string to the TextView
                        tvContents.text = wordInfo.toString()
                    }
                } else {
                    tvContents.text = "Word not found."
                }
            } catch (e: Exception) {
                tvContents.text = "An error occurred: ${e.message}"
            }
        }
    }
}