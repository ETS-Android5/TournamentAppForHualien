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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private ImageButton btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(ImageButton)findViewById(R.id.imageButton9);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        List<card> cardList = new ArrayList<>();
        cardList.add(new card("台灣牛牛肉麵", R.drawable.eat_1, "1"));
        cardList.add(new card("佳興檸檬汁", R.drawable.eat_2, "2"));
        cardList.add(new card("曾師傅麻糬", R.drawable.eat_3, "3"));
        cardList.add(new card("半天紅麻辣館", R.drawable.eat_4,"4"));
        cardList.add(new card("懷舊曲咖啡廳", R.drawable.eat_5, "5"));
        cardList.add(new card("好好吃食堂", R.drawable.eat_6, "6"));
        cardList.add(new card("野球泡芙", R.drawable.eat_7, "7"));
        cardList.add(new card("聽海小院", R.drawable.eat_8, "8"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
// MemberAdapter 會在步驟7建立
        recyclerView.setAdapter(new cardAdapter(this, cardList));
    }
    private class cardAdapter extends RecyclerView.Adapter<cardAdapter.ViewHolder> {
        private Context context;
        private List<card> cardList;

        cardAdapter(Context context, List<card> cardList) {
            this.context = context;
            this.cardList = cardList;
        }

        @Override
        public cardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cardviewlayout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final cardAdapter.ViewHolder holder, final int position) {
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
                    Intent it = new Intent(MainActivity.this,intro_container.class);
                    it.putExtra("pos",position);
                    it.putExtra("kinds","e");
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