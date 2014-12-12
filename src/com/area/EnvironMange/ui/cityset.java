package com.area.EnvironMange.ui;

/**
 * author: ${zhanghailong}
 * Date: 2014/12/11
 * Time: 21:37
 * 类的功能、说明写在此处.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;
import com.area.EnvironMange.R;
import com.area.EnvironMange.base.BaseActivity;
import com.area.EnvironMange.widget.BeizhuDialog;

public class cityset extends BaseActivity implements View.OnClickListener{
        private String[] province = new String[] {"请选择建筑物","1号楼北小树林", "办公楼","餐饮实训基地","长江大道","东操场"};
        private String[] city = new String[]{};
        private String[][] pandc = new String[][]{{"请选择楼层"},{"请选择楼层","小树林西路","小树林东路"},{"请选择楼层","楼层一","楼层二","楼层三","楼层四"},{"请选择楼层","基地楼层一","基地楼层二","基地楼层三","基地楼层四"},{"请选择楼层","大道一","大道二","大道三","大道四"},{"请选择楼层","操场北","操场南","操场西","操场东"}};
        private Spinner sp;
        private Spinner sp2;
        private Context context;

    ArrayAdapter<String> adapter ;

    ArrayAdapter<String> adapter2;

    private LinearLayout contentliner;
    private ImageView back;
    private TextView title;
    private TextView titleitem;
    private TextView beizhu;
     public void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.dafen_layout);
        context = this;
        initView();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, province);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp = (Spinner) findViewById(R.id.province);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(selectListener);

        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, city);
        adapter2.setDropDownViewResource
        (android.R.layout.simple_spinner_dropdown_item);
        sp2 = (Spinner) findViewById(R.id.city);
        sp2.setAdapter(adapter2);
        sp2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    contentliner.setVisibility(View.VISIBLE);
                    contentliner.setOnClickListener(cityset.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
     }

    private void initView() {
        contentliner = (LinearLayout) this.findViewById(R.id.contentsliner);
        contentliner.setVisibility(View.GONE);
        back = (ImageView) this.findViewById(R.id.back);
        back.setOnClickListener(this);
        title = (TextView) this.findViewById(R.id.title);
        String name  = getIntent().getExtras().getString("name");
        title.setText(name);
        titleitem = (TextView) this.findViewById(R.id.titleitem);
        titleitem.setOnClickListener(this);
        beizhu = (TextView) this.findViewById(R.id.beizhu);
        beizhu.setOnClickListener(this);
    }

    private OnItemSelectedListener selectListener = new OnItemSelectedListener(){
         public void onItemSelected(AdapterView parent, View v, int position,long id){
                 int pos = sp.getSelectedItemPosition();
                 adapter2 = new ArrayAdapter<String>
                 (context,android.R.layout.simple_spinner_item, pandc[pos]);
                 sp2.setAdapter(adapter2);
//                     Toast.makeText(cityset.this, pandc[pos].toString(), Toast.LENGTH_SHORT).show();
             }

         public void onNothingSelected(AdapterView arg0){
//                     Toast.makeText(cityset.this, "没选择", Toast.LENGTH_SHORT).show();

        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
            finish();
            break;
            case R.id.contentsliner:

                break;
            case R.id.titleitem:
                Intent score = new Intent(this, ScoreActivity.class);
                startActivity(score);
                break;
            case R.id.beizhu:
                BeizhuDialog dialog = new BeizhuDialog(this, R.style.dialog1);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
        }
    }

//    private OnItemSelectedListener selectListener1 = new OnItemSelectedListener(){
//        public void onItemSelected(AdapterView parent, View v, int position,long id){
//            int pos = sp2.getSelectedItemPosition();
//            adapter2 = new ArrayAdapter<String>
//                    (context,android.R.layout.simple_spinner_item, pandc[pos]);
//            sp2.setAdapter(adapter2);
////                     Toast.makeText(cityset.this, pandc[pos].toString(), Toast.LENGTH_SHORT).show();
//            contentliner.setVisibility(View.VISIBLE);
//        }
//
//        public void onNothingSelected(AdapterView arg0){
////                     Toast.makeText(cityset.this, "没选择", Toast.LENGTH_SHORT).show();
//
//        }
//
//    };
//弹出顶部主菜单
public void onTopMenuPopupButtonClick(View view){
    mainPopMenu.showAsDropDown(view);
}
}
