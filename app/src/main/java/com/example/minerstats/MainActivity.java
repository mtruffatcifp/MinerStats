package com.example.minerstats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minerstats.BBDD.InterficieBBDD;
import com.example.minerstats.Minero.AfegeixMinero;
import com.example.minerstats.Minero.Minero;
import com.example.minerstats.Minero.MineroAdapter;
import com.example.minerstats.Minero.MineroDetall;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int ADD_CODE = 1;
    private InterficieBBDD bd;
    private ArrayList<Minero> llistaMineros;
    private RecyclerView recyclerViewMinero;
    private MineroAdapter adapterMinero;
    private ArrayList<Long> id_miner_eliminar;
    private int DETAIL_CODE_CRYPTO = 2;
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_miner_eliminar = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar que mola molt
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                startActivityForResult(new Intent(view.getContext(), AfegeixMinero.class), ADD_CODE);
            }
        });
        llistaMineros();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        llistaMineros();
    }

    @Override
    public void onStop(){
        bd = new InterficieBBDD(this.getApplicationContext());
        bd.obre();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            id_miner_eliminar.forEach((id) -> bd.esborraMinero(id));
        }
        bd.tanca();
        super.onStop();
    }

    public void actualitzaRecycler() {
        // specify an adapter (see also next example)
        recyclerViewMinero = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerViewMinero.setHasFixedSize(true);
        // use a linear layout manager
        //layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerViewMinero.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapterMinero = new MineroAdapter(getApplicationContext(), llistaMineros, new MineroAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Minero minero) {
                Toast.makeText(getApplicationContext(), "Item Clicked"+minero.getNom(), Toast.LENGTH_LONG).show();
                //Quan clicam damunt un element de la llista
                Intent intent = new Intent(getApplicationContext(), MineroDetall.class);
                intent.putExtra("idMinero",minero.getId());
                startActivityForResult(intent,DETAIL_CODE_CRYPTO);
            }
        });
        recyclerViewMinero.setAdapter(adapterMinero);
        enableSwipe();
    }

    public void llistaMineros() {
        // Obrim la base de dades
        bd = new InterficieBBDD(this);
        bd.obre();
        // Obtenim tots els vins
        llistaMineros = bd.getAllMinero();
        bd.tanca();

        actualitzaRecycler();
    }

    private void enableSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    final Minero mineroEliminat = llistaMineros.get(position);
                    final int deletedPosition = position;
                    //guardam l'id de la pel·licula
                    id_miner_eliminar.add(mineroEliminat.getId());
                    adapterMinero.eliminaMinero(position);


                    // showing snack bar with Undo option
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), " Pel·lícula eliminada!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Desfés", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // undo is selected, restore the deleted item
                            adapterMinero.retornaMinero(mineroEliminat, deletedPosition);
                            id_miner_eliminar.remove(id_miner_eliminar.size() - 1);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;
                    p.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, p);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.elimina);
                    RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                    c.drawBitmap(icon, null, icon_dest, p);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewMinero);
    }
}