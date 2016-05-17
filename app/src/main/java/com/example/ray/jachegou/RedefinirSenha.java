package com.example.ray.jachegou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RedefinirSenha extends AppCompatActivity {
    private Button botaoRedefinir;
    private EditText emailRedefinir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        botaoRedefinir=(Button)findViewById(R.id.btnRedefinirSenha);
        emailRedefinir=(EditText)findViewById(R.id.editTextEmailRedefinir);

        botaoRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String email=emailRedefinir.getText().toString();
                    if(email.contains("@") && email.contains("com") && email.contains("hotmail.") || email.contains("gmail.") || email.contains("outlook.")){
                        Toast.makeText(RedefinirSenha.this,"Acesse seu e-mail e siga as instruções !",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RedefinirSenha.this,"E-mail ínvalido !",Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
