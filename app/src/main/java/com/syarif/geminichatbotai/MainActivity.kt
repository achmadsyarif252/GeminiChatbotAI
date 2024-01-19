package com.syarif.geminichatbotai

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.ai.client.generativeai.GenerativeModel
import com.syarif.geminichatbotai.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val generativeModel = GenerativeModel(
            // Use a model that's applicable for your use case (see "Implement basic use cases" below)
            modelName = "gemini-pro",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.apiKey
        )


        binding?.btnSend?.setOnClickListener {

            binding?.let {
                if (it.edtPrompt.text.isEmpty()) {
                    it.edtPrompt.error = "Prompt Masih Kosong!"
                    return@let
                }
                val prompt = it.edtPrompt.text.toString()
                it.tvResult.text = prompt + "\n...."
                MainScope().launch {
                    val response = generativeModel.generateContent(prompt)
                    it.tvResult.text =
                        (it.tvResult.text.toString() + "\n\n" + response.text.toString()).replace("...","")
                    it.edtPrompt.text.clear()
                }
            }
        }


    }
}