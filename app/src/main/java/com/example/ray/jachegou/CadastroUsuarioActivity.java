package com.example.ray.jachegou;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.ray.jachegou.DAOS.UsuarioWebServiceDAO;
import com.example.ray.jachegou.HELPER.FormularioUsuarioHelper;
import com.example.ray.jachegou.MODELS.UsuarioBean;
import com.google.android.gms.appindexing.Thing;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private Button btSalvar,btCancelar;
    private RequestQueue requestQueue;
    private ImageView imagemUsuario;
    private String caminhoImagem;
    private Bitmap bitmap;
    static final int REQUEST_IMAGE_OPEN = 12;
    private FormularioUsuarioHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        btSalvar=(Button)findViewById(R.id.btSavarUsuario);
        btCancelar=(Button)findViewById(R.id.btCancelarUsuario);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        imagemUsuario=(ImageView)findViewById(R.id.cadastroFotoUsuario);
        helper=new FormularioUsuarioHelper(this);
        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.setBitmap(bitmap);
                helper.salvar();
            }
        });
        imagemUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_IMAGE_OPEN) {
            Uri fullPhotoUri = intent.getData();
            try{
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),fullPhotoUri);
                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 500, 400, true);
                imagemUsuario.setImageBitmap(resized);
                this.bitmap=resized;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public String getPath(Uri uri){
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
    public Bitmap getBitmap(){
        return this.bitmap;
    }
}
