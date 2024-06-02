package com.example.hamburgueriaz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int quantity = 0;
    private TextView textViewQuantity;
    private TextView textViewTotalPrice;
    private EditText editTextName;
    private CheckBox checkboxBacon, checkboxCheese, checkboxOnionRings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewQuantity = findViewById(R.id.textViewQuantity);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        editTextName = findViewById(R.id.editTextName);
        checkboxBacon = findViewById(R.id.checkboxBacon);
        checkboxCheese = findViewById(R.id.checkboxCheese);
        checkboxOnionRings = findViewById(R.id.checkboxOnionRings);

        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                somar();
            }
        });

        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtrair();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarPedido();
            }
        });
    }

    private void somar() {
        quantity++;
        textViewQuantity.setText(String.valueOf(quantity));
        updateTotalPrice();
    }

    private void subtrair() {
        if (quantity > 0) {
            quantity--;
            textViewQuantity.setText(String.valueOf(quantity));
            updateTotalPrice();
        }
    }

    private void updateTotalPrice() {
        int pricePerBurger = 20;
        int additionalBacon = checkboxBacon.isChecked() ? 2 : 0;
        int additionalCheese = checkboxCheese.isChecked() ? 2 : 0;
        int additionalOnionRings = checkboxOnionRings.isChecked() ? 3 : 0;

        int totalPrice = quantity * (pricePerBurger + additionalBacon + additionalCheese + additionalOnionRings);
        textViewTotalPrice.setText("Preço Total: R$ " + totalPrice);
    }

    private void enviarPedido() {
        String nome = editTextName.getText().toString();
        boolean hasBacon = checkboxBacon.isChecked();
        boolean hasCheese = checkboxCheese.isChecked();
        boolean hasOnionRings = checkboxOnionRings.isChecked();
        int pricePerBurger = 20;

        int totalPrice = quantity * (pricePerBurger + (hasBacon ? 2 : 0) + (hasCheese ? 2 : 0) + (hasOnionRings ? 3 : 0));

        String resumoPedido = "Nome do cliente: " + nome +
                "\nTem Bacon? " + (hasBacon ? "Sim" : "Não") +
                "\nTem Queijo? " + (hasCheese ? "Sim" : "Não") +
                "\nTem Onion Rings? " + (hasOnionRings ? "Sim" : "Não") +
                "\nQuantidade: " + quantity +
                "\nPreço final: R$ " + totalPrice;

        TextView resumoTextView = findViewById(R.id.textViewResumoPedido);
        resumoTextView.setText(resumoPedido);

        enviarEmail(nome, resumoPedido);
    }

    private void enviarEmail(String nome, String resumoPedido) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido de " + nome);
        intent.putExtra(Intent.EXTRA_TEXT, resumoPedido);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
