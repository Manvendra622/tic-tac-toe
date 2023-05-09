package com.example.newtictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.newtictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Turn{
        NOUGHT,
        CROSS
    }

    private var firstTurn=Turn.CROSS
    private var currentTurn=Turn.CROSS

    private var NOUGHTSCORE=0
    private var CROSSSCORE=0

    private lateinit var binding: ActivityMainBinding
    private var boardList= mutableListOf<Button>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun clickBoard(view: View){
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(NOUGHT)){
            NOUGHTSCORE++
            result("Nought won")
        }
        if(checkForVictory(CROSS)){
            CROSSSCORE++
            result("Cross won")
        }
        if(fullBoard()){
            result("Draw")
        }

    }

    private fun checkForVictory(s: String): Boolean {
        //Horizontal Victory
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true
        //Vertical Victory
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true
        //Diagonal Victory
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true

        return false
    }

    private fun match(button: Button, symbol: String):Boolean= button.text==symbol

    private fun result(title: String) {
        val message="NOUGHT= $NOUGHTSCORE \n CROSS= $CROSSSCORE"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            {_,_ ->
                resetBoard()

            }.setCancelable(false)
            .show()

    }

    private fun resetBoard() {
        for(button in boardList){
            button.text=""
        }
        if(firstTurn==Turn.NOUGHT){
            firstTurn=Turn.CROSS
        }
        else if(firstTurn==Turn.CROSS){
            firstTurn=Turn.NOUGHT
        }
        currentTurn= firstTurn
        setTextLabel()

    }

    private fun fullBoard(): Boolean {
        for(button in boardList){
            if(button.text=="")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if(button.text!= "")
            return

        if(currentTurn==Turn.NOUGHT){
            button.text= NOUGHT
            currentTurn=Turn.CROSS
        }
        else if(currentTurn==Turn.CROSS){
            button.text= CROSS
            currentTurn=Turn.NOUGHT
        }
        setTextLabel()
    }

    private fun setTextLabel() {
        var turnText=""
        if(currentTurn==Turn.CROSS){
            turnText="Turn $CROSS"
        }
        else if(currentTurn==Turn.NOUGHT){
            turnText="Turn $NOUGHT"
        }
        binding.turnX.text=turnText
    }

    companion object{
        var NOUGHT="0"
        var CROSS="X"
    }

}