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

public class MainActivity3 extends AppCompatActivity {
private ImageButton btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        btn1=(ImageButton)findViewById(R.id.imageButton11);
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        List<card> cardList = new ArrayList<>();
        cardList.add(new card("新城車站", R.drawable.fun_1, "1"));
        cardList.add(new card("新城天主堂", R.drawable.fun_2, "2"));
        cardList.add(new card("練習曲書店", R.drawable.fun_3, "3"));
        cardList.add(new card("新城國小棒球隊", R.drawable.fun_4,"4"));
        cardList.add(new card("新城照相館", R.drawable.fun_5, "5"));
        cardList.add(new card("練習曲X新城藝術電力公司", R.drawable.fun_6, "6"));
        cardList.add(new card("斯土有情陶藝坊", R.drawable.fun_7, "7"));
        cardList.add(new card("新城海堤", R.drawable.fun_8, "8"));
        cardList.add(new card("戶外亦溯", R.drawable.fun_9, "9"));
        cardList.add(new card("米西亞莊園  Mysia farmland", R.drawable.fun_10, "10"));


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview3);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity3.this));
// MemberAdapter 會在步驟7建立
        recyclerView.setAdapter(new MainActivity3.cardAdapter(this, cardList));
    }
    private class cardAdapter extends RecyclerView.Adapter<MainActivity3.cardAdapter.ViewHolder> {
        private Context context;
        private List<card> cardList;

        cardAdapter(Context context, List<card> cardList) {
            this.context = context;
            this.cardList = cardList;
        }

        @Override
        public MainActivity3.cardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.cardviewlayout, parent, false);
            return new MainActivity3.cardAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MainActivity3.cardAdapter.ViewHolder holder, final int position) {
            final card member = cardList.get(position);
            holder.imageId.setImageResource(member.getImage());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(MainActivity3.this,intro_container.class);
                    it.putExtra("pos",position);
                    it.putExtra("kinds","p");
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