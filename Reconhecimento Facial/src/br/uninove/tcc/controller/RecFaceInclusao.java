package br.uninove.tcc.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.uninove.tcc.R;
import br.uninove.tcc.dao.UsuarioDAO;
import br.uninove.tcc.model.Usuario;
import br.uninove.tcc.util.ClassUtil;

public class RecFaceInclusao extends Activity {
	
	private EditText nome;
	private Usuario usuario = new Usuario();
	private final int TELA_GERENCIAL = 1275417112;
	private final int TIRAR_FOTO = 1298372137;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar_usuario);
        
        nome = (EditText) findViewById(R.id.nome);
        Button capturarImagemButton = (Button) findViewById(R.id.capturar_imagem);
        Button voltarButton = (Button) findViewById(R.id.voltar);
        
        capturarImagemButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//cria Intent e adiciona o caminho a ser gravado
				Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(camera, TIRAR_FOTO);
				
			}
		});
        
        voltarButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				voltar();
				finish();
			}
		});
    }
       
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if(requestCode == TIRAR_FOTO){
			if (resultCode == RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                if(bmp != null){
					ClassUtil util = new ClassUtil();
					usuario.setNome(nome.getEditableText().toString());
					usuario.setCaracteristicas(util.tratarImagem(bmp));
					UsuarioDAO usuarioDAO = new UsuarioDAO(RecFaceInclusao.this);
					usuarioDAO.adicionarUsuario(usuario);
					usuarioDAO.close();
				}
               
            } else if (resultCode == RESULT_CANCELED) {
                Toast cancelou = Toast.makeText(this, "Cancelou", Toast.LENGTH_LONG);
                cancelou.show();
            } else {
                Toast saiu = Toast.makeText(this, "Saiu", Toast.LENGTH_SHORT);
                saiu.show();
            }
		}
    	
    	finish();
    	
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    }
    
    private void voltar() {
		Intent i = new Intent(this, RecFaceGerencial.class);
		startActivityForResult(i, TELA_GERENCIAL);
	}


}