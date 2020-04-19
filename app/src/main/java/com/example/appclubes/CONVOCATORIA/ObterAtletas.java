package com.example.appclubes.CONVOCATORIA;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appclubes.ATLETA.Atleta;
import com.example.appclubes.CustomAdapter;
import com.example.appclubes.ListViewItemDTO;
import com.example.appclubes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ObterAtletas extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custompopup);

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        // Criar POP UP Window
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        // Get listview checkbox.
        //final ListView listViewWithCheckbox = (ListView)findViewById(R.id.list_view_with_checkbox);
        final ListView listView = (ListView) findViewById(R.id.lista);

        // Initiate listview data.
        final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList();

        // Create a custom list view adapter with checkbox control.
        final CustomAdapter listViewDataAdapter = new CustomAdapter(getApplicationContext(), initItemList);

        listViewDataAdapter.notifyDataSetChanged();

        // Set data adapter to list view.
        listView.setAdapter(listViewDataAdapter);

        // When list view item is clicked.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                // Get user selected item.
                Object itemObject = adapterView.getAdapter().getItem(itemIndex);

                // Translate the selected item to DTO object.
                ListViewItemDTO itemDto = (ListViewItemDTO) itemObject;

                // Get the checkbox.
                CheckBox itemCheckbox = (CheckBox) findViewById(R.id.checkbox);

                // Reverse the checkbox and clicked item check state.
                if (itemDto.isChecked()) {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);
                } else {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);
                }

                //Toast.makeText(getApplicationContext(), "select item text : " + itemDto.getItemText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
        // Return an initialize list of ListViewItemDTO.
        private List<ListViewItemDTO> getInitViewItemDtoList()
        {

            reference.child("atleta").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        // Fazer IF tendo em conta o escalão selecionado

                        String NameAtleta[] = {postSnapshot.child("nome").getValue().toString()};

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

            String itemTextArr[] = {"Android", "iOS", "Java", "JavaScript", "JDBC", "JSP", "Linux", "Python", "Servlet", "Windows"};
            //String itemTextArr[] = NameAtleta;

            List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();
            int length = itemTextArr.length;

            for(int i=0;i<length;i++)
            {
                String itemText = itemTextArr[i];

                ListViewItemDTO dto = new ListViewItemDTO();
                dto.setChecked(false);
                dto.setItemText(itemText);

                ret.add(dto);
            }

            return ret;
        }
    }


