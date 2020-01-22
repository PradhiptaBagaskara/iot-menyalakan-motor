package bon.ruvio.smartkeyvehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import at.markushi.ui.CircleButton;

public class MainActivity extends AppCompatActivity {

    CircleButton starter;
    TextView tes;
    Switch mesin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference kunciDB = database.getReference("kunci");
        final DatabaseReference starterDB = database.getReference("starter");
        starter = (CircleButton) findViewById(R.id.starter);
        starter.setVisibility(View.GONE);

        mesin = findViewById(R.id.kunci);
        mesin.setChecked(false);
        kunciDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean dt = dataSnapshot.getValue(Boolean.class);
                if(dt){
                    mesin.setChecked(true);
                    mesin.setText("Mesin Menyala         ");

                    starter.setVisibility(View.VISIBLE);
                }else {
                    mesin.setChecked(false);
                    mesin.setText("Mesin Mati         ");
                    starter.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mesin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                kunciDB.setValue(b);
            }
        });

        starter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        starterDB.setValue(true);
                        starter.setColor(getResources().getColor(R.color.starter));

                        // PRESSED
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        starterDB.setValue(false);
                        starter.setColor(getResources().getColor(R.color.colorAccent));

                        // RELEASED
                        break;
                }
                return false;
            }
        });

    }
}
