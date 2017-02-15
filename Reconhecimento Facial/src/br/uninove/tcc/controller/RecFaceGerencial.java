package br.uninove.tcc.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import br.uninove.tcc.R;
import br.uninove.tcc.dao.UsuarioDAO;
import br.uninove.tcc.model.Usuario;
import br.uninove.tcc.util.ClassUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RecFaceGerencial extends Activity {
	
	private Usuario usuario = new Usuario();
	private final int TELA_INCLUSAO = 1273512312;
	private final int TELA_PESQUISA = 1237816321;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_gerencial);
        
        Button cadastrarButton = (Button) findViewById(R.id.cadastrar_usuario);
        Button pesquisarButton = (Button) findViewById(R.id.pesquisar);
        Button apagarButton = (Button) findViewById(R.id.apagar_banco);
        
        
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
			
        	@Override
			public void onClick(View v) {
				cadastrarUsuario();
				
			}
		});
        
        apagarButton.setOnClickListener(new View.OnClickListener() {
			
        	@Override
			public void onClick(View v) {
				UsuarioDAO usuarioDAO = new UsuarioDAO(RecFaceGerencial.this);
				usuarioDAO.onUpgrade(usuarioDAO.getWritableDatabase(), 1, 2);
				mensagemDeletado();
				usuarioDAO.close();
				
			}
		});
        
        pesquisarButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, TELA_PESQUISA);
				
			}
		});
    }
    
    private void cadastrarUsuario() {
		Intent i = new Intent(this, RecFaceInclusao.class);
		startActivityForResult(i, TELA_INCLUSAO);
	}

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	super.onActivityResult(requestCode, resultCode, data);
		
    	if(requestCode == TELA_PESQUISA){
			if (resultCode == RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                if(bmp != null){
                	ClassUtil util = new ClassUtil();
					usuario.setCaracteristicas(util.tratarImagem(bmp));
					List<Usuario> listaUsuarios = new ArrayList<Usuario>();
					UsuarioDAO usuarioDAO = new UsuarioDAO(RecFaceGerencial.this);
					listaUsuarios = usuarioDAO.obterUsuarios();
					boolean flag = false;
					for (Usuario itemLista : listaUsuarios) {
						if(util.compararImagens(util.obterVetor(usuario.toString()), util.obterVetor(itemLista.toString()))){
							
							Toast popup = Toast.makeText(this, "Encontrou:  " + itemLista.getNome(), Toast.LENGTH_LONG);
							popup.show();
							
							flag = true;
							
							break;
							
						}
					}
					if(!flag){
						Toast naoEncontrou = Toast.makeText(this, "Não foi encontrado nenhum usuário. ", Toast.LENGTH_LONG);
						naoEncontrou.show();
					}
					usuarioDAO.close();
				}
               
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelou", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(this, "Saiu", Toast.LENGTH_SHORT);
            }
		}
	}
    
    public void mensagemDeletado(){
    	Toast deletado = Toast.makeText(this, "Bando de dados deletado.", Toast.LENGTH_LONG);
		deletado.show();
    }
}
