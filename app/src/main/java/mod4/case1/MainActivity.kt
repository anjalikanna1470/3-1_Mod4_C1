package mod4.case1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etInput = findViewById<EditText>(R.id.etInput)
        val btnProcess = findViewById<Button>(R.id.btnProcess)
        val tvSummary = findViewById<TextView>(R.id.tvSummary)
        val tvClass = findViewById<TextView>(R.id.tvClass)
        val tvRewritten = findViewById<TextView>(R.id.tvRewritten)

        btnProcess.setOnClickListener {
            val input = etInput.text.toString()
            if (input.isNotEmpty()) {
                processUserInput(input, tvSummary, tvClass, tvRewritten)
            } else {
                Toast.makeText(this, getString(R.string.empty_input_warning), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun processUserInput(
        text: String,
        tvSummary: TextView,
        tvClass: TextView,
        tvRewritten: TextView
    ) {
        lifecycleScope.launch {
            try {
                tvSummary.text = getString(R.string.loading)
                tvClass.text = getString(R.string.loading)
                tvRewritten.text = getString(R.string.loading)

                val response = ApiClient.apiService.processText(LLMRequest(text))

                tvSummary.text = response.summary
                tvClass.text = response.category
                tvRewritten.text = response.rewritten

            } catch (e: Exception) {
                e.printStackTrace()
                tvSummary.text = getString(R.string.error)
                tvClass.text = getString(R.string.error)
                tvRewritten.text = getString(R.string.error)
                Toast.makeText(this@MainActivity, "Network Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}