package br.com.redesenhe.protect.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import br.com.redesenhe.protect.R;
import br.com.redesenhe.protect.util.UtilSenha;


public class CustomDialogConfiguracaoSenha extends AppCompatDialogFragment {

    // Componentes
    private EditText editTextSenhaGerada;
    private Button btnGerarSenha;
    private EditText editTextTamanhoSenha;
    private Button btnTamanhoSenha6;
    private Button btnTamanhoSenha8;
    private Button btnTamanhoSenha12;
    private CheckBox ckMaiuscula;
    private CheckBox ckMinuscula;
    private CheckBox ckDigito;
    private CheckBox ckUnderline;
    private CheckBox ckEspecial;

    // View
    private View view;

    // Listener
    private CustomDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_dialog_senha, null);
        builder.setView(view)
                .setTitle("Gerar Senha")
                .setPositiveButton("Aceitar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String senhaGerada = editTextSenhaGerada.getText().toString();
                        listener.applyText(senhaGerada);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        init();

        return builder.create();
    }

    private void init() {
        editTextSenhaGerada = view.findViewById(R.id.dialog_senha_senha);
        btnGerarSenha = view.findViewById(R.id.dialog_senha_buttonGerarSenha);
        editTextTamanhoSenha = view.findViewById(R.id.dialog_senha_tamanhoSenha);
        btnTamanhoSenha6 = view.findViewById(R.id.dialog_senha_buttonTamanho6);
        btnTamanhoSenha8 = view.findViewById(R.id.dialog_senha_buttonTamanho8);
        btnTamanhoSenha12 = view.findViewById(R.id.dialog_senha_buttonTamanho12);
        ckMaiuscula = view.findViewById(R.id.dialog_senha_checkBox_maiuscula);
        ckMinuscula = view.findViewById(R.id.dialog_senha_checkBox_minuscula);
        ckDigito = view.findViewById(R.id.dialog_senha_checkBox_digitos);
        ckUnderline = view.findViewById(R.id.dialog_senha_checkBox_underline);
        ckEspecial =view.findViewById(R.id.dialog_senha_checkBox_especial);

        editTextTamanhoSenha.setText("8");
        ckMaiuscula.setChecked(true);
        ckMinuscula.setChecked(true);
        ckDigito.setChecked(true);

        geraSenha();

        btnGerarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geraSenha();
            }
        });
        btnTamanhoSenha6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTamanhoSenha.setText("6");
            }
        });
        btnTamanhoSenha8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTamanhoSenha.setText("8");
            }
        });
        btnTamanhoSenha12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextTamanhoSenha.setText("12");
            }
        });
    }

    private void geraSenha(){
        // verifica campos
        if (editTextTamanhoSenha.getText().toString().trim().isEmpty()){
            Toast.makeText(getActivity(), "Tamanho não Informado!", Toast.LENGTH_LONG).show();
            return;
        }

        int tamanho = Integer.parseInt(editTextTamanhoSenha.getText().toString());
        Boolean maiusculo = ckMaiuscula.isChecked();
        Boolean minusculo = ckMinuscula.isChecked();
        Boolean digito = ckDigito.isChecked();
        Boolean underline = ckUnderline.isChecked();
        Boolean especial = ckEspecial.isChecked();

        String senhaGerada = UtilSenha.gerarSenha(tamanho, maiusculo, minusculo, digito, underline, especial);

        editTextSenhaGerada.setText(senhaGerada);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CustomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Tem que implementar CustomDialogNovoGrupo");
        }

    }

    public interface CustomDialogListener{
        void applyText(String senhaGerada);
    }


}
