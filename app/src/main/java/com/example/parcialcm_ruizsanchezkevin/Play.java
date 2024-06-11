package com.example.parcialcm_ruizsanchezkevin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Play extends AppCompatActivity {

    private final Button[] buttons = new Button[9];
    private final int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Filas
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columnas
            {0, 4, 8}, {2, 4, 6}             // Diagonales
    };
    private String currentPlayer = "X";
    private final String[] board = new String[9];
    private final List<Integer> xPositions = new ArrayList<>();
    private final List<Integer> oPositions = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        TextView textViewWelcome = findViewById(R.id.textViewWelcome);
        Button buttonBack = findViewById(R.id.buttonBack);

        // Obtener el nombre de usuario desde el Intent
        Intent intent = getIntent();
        @SuppressLint("SetTextI18n") String username = intent.getStringExtra("username");
        textViewWelcome.setText("Bienvenido " + username);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            buttons[i] = (Button) gridLayout.getChildAt(i);
            final int index = i;
            buttons[i].setOnClickListener(v -> onButtonClick(index));
        }

        buttonBack.setOnClickListener(v -> {
            finish(); // Regresar a la actividad anterior
        });
    }

    private void onButtonClick(int index) {
        if (board[index] != null) return;

        board[index] = currentPlayer;
        buttons[index].setText(currentPlayer);

        if (currentPlayer.equals("X")) {
            xPositions.add(index);
            if (xPositions.size() > 3) {
                int oldestX = xPositions.remove(0);
                board[oldestX] = null;
                buttons[oldestX].setText("");
            }
        } else {
            oPositions.add(index);
            if (oPositions.size() > 3) {
                int oldestO = oPositions.remove(0);
                board[oldestO] = null;
                buttons[oldestO].setText("");
            }
        }

        if (checkForWin()) {
            Toast.makeText(this, "El jugador " + currentPlayer + " ha ganado!", Toast.LENGTH_SHORT).show();
            resetBoard();
        } else if (isBoardFull()) {
            Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show();
            resetBoard();
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        }
    }

    private boolean checkForWin() {
        for (int[] winPosition : winningPositions) {
            if (board[winPosition[0]] != null &&
                    board[winPosition[0]].equals(board[winPosition[1]]) &&
                    board[winPosition[0]].equals(board[winPosition[2]])) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (String cell : board) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = null;
            buttons[i].setText("");
        }
        currentPlayer = "X";
        xPositions.clear();
        oPositions.clear();
    }
}
