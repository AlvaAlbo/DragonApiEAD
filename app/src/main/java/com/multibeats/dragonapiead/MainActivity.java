package com.multibeats.dragonapiead;

/* test */

/* prueba correo github */

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CharacterAdapter adapter;
    private ArrayList<Character> characterList;
    // URL de la API
    private String url = "https://dragonball-api.com/api/characters/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista de personajes
        characterList = new ArrayList<>();

        // Inicializar el adaptador
        adapter = new CharacterAdapter(this, characterList);

        // Establecer el adaptador en el RecyclerView
        recyclerView.setAdapter(adapter);

        // Obtener los personajes
        fetchCharacters();

    }

    private void fetchCharacters() {
        // Crear una nueva cola de peticiones
        RequestQueue queue = Volley.newRequestQueue(this);

        //Crear una nueva petición JSON a la URL de la API de personajes, con los parametros GET, null, y dos listeners
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Obtener el array de personajes de la respuesta
                        JSONArray items = response.getJSONArray("items");

                        // Recorrer el array de personajes
                        for (int i = 0; i < items.length(); i++) {
                            // Obtener el objeto JSON de cada personaje
                            JSONObject item = items.getJSONObject(i);

                            // Obtener los atributos del personaje
                            String name = item.getString("name");
                            String image = item.getString("image");
                            String description = item.getString("description");

                            // Crear un nuevo objeto Character y añadirlo a la lista, con los atributos obtenidos
                            characterList.add(new Character(
                                    item.getString("name"),
                                    item.getString("image"),
                                    item.getString("description"),
                                    item.getString("race"),
                                    item.getString("ki"),
                                    item.getString("maxKi"),
                                    item.getString("gender")
                            ));

                        }

                        // Notificar cambios al adaptador
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                    }
                },
                // Manejar errores de conexión
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show();
                });

        // Añadir la petición a la cola
        queue.add(request);
    }

}