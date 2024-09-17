package br.com.faculdadeimpacta.asynctask

import android.R
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.faculdadeimpacta.asynctask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()

        val tarefa = Temporizador()
        tarefa.execute("AsyncTask")

        binding.buttonStart.setOnClickListener {
            binding.textView.text = tarefa.status.toString()
        }
    }

    inner class Temporizador : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            binding.progressBar.max = 10
            binding.textView.text = "onPreExecute"
        }

        override fun doInBackground(vararg p0: String?): String {
            for (i in 0..10) {
                Thread.sleep(1000)
                publishProgress(i)
            }
            return "Terminei"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            binding.progressBar.progress = values[0]!!
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            binding.textView.text = "${this.status.toString()}\n${result}"
        }

    }
}