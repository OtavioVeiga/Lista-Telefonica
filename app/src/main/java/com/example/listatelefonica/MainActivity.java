package com.example.listatelefonica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import banco_dados.ContatosDB;
import banco_dados.DBHelper;

public class MainActivity extends AppCompatActivity {

    EditText nome;
    EditText telefone;
    EditText datanasc;
    Button salvar;
    ListView listagem;
    List<Contatos> dados;
    DBHelper dbHelper;
    ContatosDB contatosDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        setContentView(R.layout.activity_main);

        nome = findViewById(R.id.nomeId);
        telefone = findViewById(R.id.phoneId);
        datanasc = findViewById(R.id.dataId);
        listagem = findViewById(R.id.listagem);

        dados = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dados);
        listagem.setAdapter(adapter);

        contatosDB = new ContatosDB(dbHelper);
        contatosDB.lista(dados);

        listagem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
                alert.setMessage("Confirmar");
                alert.setPositiveButton("remover", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){

                    }
                });
                alert.create().show();
                return false;
            }
        });
    }

    public void salvar(View view){
        Contatos contatos = new Contatos();
        contatos.setNome(nome.getText().toString());
        contatos.setTelefone(telefone.getText().toString());
        dados.add(contatos);
        contatosDB.inserir(contatos);
        contatosDB.inserir(contatos);
        contatosDB.lista(dados);
        Toast.makeText(this, "Salvo com Sucesso!!!", Toast.LENGTH_LONG).show();

    }
}