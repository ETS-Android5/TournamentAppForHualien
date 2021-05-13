package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
private ImageButton btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1=(ImageButton)findViewById(R.id.imageButton10);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        List<card> cardList = new ArrayList<>();
        cardList.add(new card("太魯閣蘇西小空間", R.drawable.live_1, "1"));
        cardList.add(new card("太魯閣立閣人文旅店", R.drawable.live_2, "2"));



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
// MemberAdapter 會在步驟7建立
        recyclerView.setAdapter(new MainActivity2.cardAdapter(this, cardList));
    }
    private class cardAdapter extends RecyclerView.Adapter<MainActivity2.cardAdapter.ViewHolder> {
        private Context context;
        private List<card> cardList;

        cardAdapter(Context context, List<card> cardList) {
            this.context = context;
            this.cardList = cardList;
        }

        @Override
        public MainActivity2.cardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cardviewlayout, parent, false);
            return new MainActivity2.cardAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MainActivity2.cardAdapter.ViewHolder holder, final int position) {
            final card member = cardList.get(position);
            holder.imageId.setImageResource(member.getImage());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*ImageView imageView = new ImageView(context);
                    imageView.setImageResource(member.getImage());
                    Toast toast = new Toast(context);
                    toast.setView(imageView);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                    */
                    Intent it = new Intent(MainActivity2.this,intro_container.class);
                    it.putExtra("pos",position);
                    it.putExtra("kinds","l");
                    startActivity(it);
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }

        //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
        class ViewHolder extends RecyclerView.ViewHolder{
            ImageView imageId;
            ViewHolder(View itemView) {
                super(itemView);
                imageId = (ImageView) itemView.findViewById(R.id.imageId);
            }
        }
    }
}