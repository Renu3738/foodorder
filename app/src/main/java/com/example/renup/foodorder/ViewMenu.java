package com.example.renup.foodorder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ViewMenu extends AppCompatActivity {
    ArrayList<menuList> a=new ArrayList();
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_menu);
        rv=(RecyclerView)findViewById(R.id.rv);
        showData();
        Adapterclass adapterclass=new Adapterclass();
        rv.setAdapter(adapterclass);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }

    public class Adapterclass extends RecyclerView.Adapter<Vholder>{

        @NonNull
        @Override
        public Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view=getLayoutInflater().inflate(R.layout.singalmenu, parent, false);
            Vholder vholder=new Vholder(view);
            return vholder;
        }

        @Override
        public void onBindViewHolder(@NonNull Vholder holder, int position) {
            holder.txtimage.setText(a.get(position).getImage().toString());
            holder.txtname.setText(a.get(position).getName().toString());
            holder.txtprice.setText(a.get(position).getPrice().toString());
            holder.txthalfprice.setText(a.get(position).getHalfprice().toString());
        }

        @Override
        public int getItemCount() {
            return a.size();
        }
    }
    public class Vholder extends RecyclerView.ViewHolder{
        TextView txtimage,txtname, txtprice, txthalfprice;

        public Vholder(@NonNull View itemView) {
            super(itemView);
            txtimage=itemView.findViewById(R.id.txtimage);
            txtname=itemView.findViewById(R.id.txtcost);
            txtprice=itemView.findViewById(R.id.txtprice);
            txthalfprice=itemView.findViewById(R.id.txthalfprice);
        }
    }

    public class menuList{
        String image;
        String name;
        String price;
        String halfprice;

        public menuList(String image, String name, String price, String halfprice) {
            this.image = image;
            this.name = name;
            this.price = price;
            this.halfprice = halfprice;
        }
        public String getImage() {
            return image;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public String getHalfprice() {
            return halfprice;
        }

    }

    private void showData(){
        String url="http://renupun.com.np/food/request.php?getdata";
        StringRequest stringRequest=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i = 0; i <jsonArray.length() ; i++){
                        String image=jsonArray.getJSONObject(i).getString("image");
                        String name=jsonArray.getJSONObject(i).getString("name");
                        String price=jsonArray.getJSONObject(i).getString("price");
                        String halfprice=jsonArray.getJSONObject(i).getString("halfprice");
                        a.add(new menuList(image,name,price,halfprice));
                        Adapterclass adapterclass=new Adapterclass();
                        rv.setAdapter(adapterclass);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
