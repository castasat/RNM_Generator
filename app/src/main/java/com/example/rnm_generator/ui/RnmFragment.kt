package com.example.rnm_generator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.rnm_generator.R
import com.example.rnm_generator.ui.MainActivity.Companion.log
import com.example.rnm_generator.viewmodel.RnmViewModel

class RnmFragment : Fragment() {

    // views
    private var innEditText: AppCompatEditText? = null
    private var factoryNumberEditText: AppCompatEditText? = null
    private var rnmTextView: AppCompatTextView? = null
    private var generateButton: AppCompatButton? = null

    private val rnmViewModel: RnmViewModel by lazy {
        ViewModelProviders.of(this)[RnmViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.rnm_fragment, container, false)
        findViewsByIds(view)
        observeRnm()
        initializeComponents()
        return view
    }

    private fun observeRnm() {
        rnmViewModel.rnmLiveData.observe(viewLifecycleOwner,
            Observer<String> { rnm ->
                log("RnmFragment.observeRnm(): rnm = $rnm")
                rnmTextView?.text = rnm
            })
    }

    private fun initializeComponents() {
        generateButton?.setOnClickListener {
            innEditText?.let { innEditText ->
                factoryNumberEditText?.let { factoryNumberEditText ->
                    val inn = innEditText.text.toString()
                    val factoryNumber = factoryNumberEditText.text.toString()
                    log("RnmFragment.initializeComponents(): inn = $inn")
                    log("RnmFragment.initializeComponents(): factoryNumber = $factoryNumber")
                    rnmViewModel.generateRnm(inn, factoryNumber)
                }
            }
        }
    }

    private fun findViewsByIds(view: View?) {
        view?.run {
            innEditText = findViewById(R.id.innEditText)
            factoryNumberEditText = findViewById(R.id.factoryNumberEditText)
            rnmTextView = findViewById(R.id.rnmTextView)
            generateButton = findViewById(R.id.generateButton)
        }
    }
}
