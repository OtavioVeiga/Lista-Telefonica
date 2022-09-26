package com.example.listatelefonica;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    ListView listagem;
    List<Contatos> dados;
    DBHelper db;
    ContatosDB contatosDB;
    Integer atualiza;
    Integer confirma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        nome = findViewById(R.id.nomeId);
        telefone = findViewById(R.id.phoneId);
        datanasc = findViewById(R.id.dataId);
        listagem = findViewById(R.id.listagem);
        dados = new ArrayList<>();


        ArrayAdapter adapter = new ArrayAdapter(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dados);
        listagem.setAdapter(adapter);

        contatosDB = new ContatosDB(db);
        contatosDB.lista(dados);
        acoes();
    }
    private void acoes() {
        confirma = null;
        listagem.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AlertDialog.Builder mensagem = new AlertDialog.Builder(view.getContext());
                        mensagem.setTitle("Opções");
                        mensagem.setMessage("Escolha uma das opções que deseja");
                        mensagem.setPositiveButton("Remover", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                new AlertDialog.Builder(view.getContext())
                                        .setMessage("Deseja realmente remover o usuario ?")
                                        .setPositiveButton("Confirmar",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface,
                                                                        int k) {
                                                        contatosDB.remover(dados.get(i).getId());
                                                        contatosDB.lista(dados);
                                                        ((ArrayAdapter)listagem.getAdapter()
                                                        ).notifyDataSetChanged();
                                                        String msg1 = "Contato removido!!";
                                                        Toast.makeText(getApplicationContext(), msg1, Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                        .setNegativeButton("cancelar", null)
                                        .create().show();
                            }
                        });
                        mensagem.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                atualiza = dados.get(i).getId();
                                nome.setText(dados.get(i).getNome());
                                telefone.setText(dados.get(i).getTelefone().toString());
                                datanasc.setText(dados.get(i).getDatanasc().toString());

                                contatosDB.atualizar(dados.get(i));
                                contatosDB.lista(dados);

                                confirma = 1;

                            }
                        });
                        mensagem.setNeutralButton("Cancelar", null);
                        mensagem.show();
                        return false;
                    }
                });
    }

    public boolean verificar(){
        String s1 = nome.getText().toString();
        String s2 = telefone.getText().toString();
        String s3 = datanasc.getText().toString();

        if ((s1.equals("") || s2.equals(null) || s3.equals(null)) ||
                (s1.equals("") || s2.equals("") || s3.equals(""))){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    public void salvar(View view){
        if (verificar()){
            Contatos contatos = new Contatos();
            if (atualiza != null){
                contatos.setId(atualiza);

                contatosDB.lista(dados);
                Toast.makeText(this, "Salvo com Sucesso!!!", Toast.LENGTH_SHORT).show();
            }

            contatos.setNome(nome.getText().toString());
            contatos.setTelefone(telefone.getText().toString());
            contatos.setDatanasc(datanasc.getText().toString());

            if (atualiza != null)
                contatosDB.atualizar(contatos);

            else {
                contatosDB.inserir(contatos);
                contatosDB.lista(dados);
                Toast.makeText(this, "Salvo com Sucesso!!!", Toast.LENGTH_SHORT).show();
            }

            contatosDB.lista(dados);
            listagem.invalidateViews();
            limpar();
            atualiza = null;
            confirma = null;
        }
    }

    private void limpar() {
        nome.setText("");
        telefone.setText("");
        datanasc.setText("");
    }

    @Override
    public void onBackPressed() {
        if (confirma != null) {
            super.onBackPressed();
            limpar();
            String msgCancelar = "Edição não Realizada";
            Toast.makeText(getApplicationContext(), msgCancelar, Toast.LENGTH_SHORT).show();
            confirma = null;
        }
        else {
            super.onBackPressed();
            limpar();
            String msgSair = "Saindo";
            Toast.makeText(getApplicationContext(), msgSair, Toast.LENGTH_SHORT).show();
        }
    }

}