package com.multibeats.dragonapiead;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Character> characterList;

    public CharacterAdapter(Context context, ArrayList<Character> characterList) {
        this.context = context;
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_character, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character character = characterList.get(position);

        holder.nameTextView.setText(character.getName());
        holder.descriptionTextView.setText(character.getDescription());
        holder.raceTextView.setText("Race: " + character.getRace());
        holder.kiTextView.setText("KI: " + character.getKi());
        holder.maxKiTextView.setText("Max KI: " + character.getMaxKi());
        holder.genderTextView.setText("Gender: " + character.getGender());

        // Cargar la imagen
        Glide.with(context)
                .load(character.getImageUrl())
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView, descriptionTextView, raceTextView, kiTextView, maxKiTextView, genderTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.character_image);
            nameTextView = itemView.findViewById(R.id.character_name);
            descriptionTextView = itemView.findViewById(R.id.character_description);
            raceTextView = itemView.findViewById(R.id.character_race);
            kiTextView = itemView.findViewById(R.id.character_ki);
            maxKiTextView = itemView.findViewById(R.id.character_max_ki);
            genderTextView = itemView.findViewById(R.id.character_gender);
        }
    }
}
