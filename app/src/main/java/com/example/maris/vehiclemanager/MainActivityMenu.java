package com.example.maris.vehiclemanager;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.maris.vehiclemanager.Fragments.CategoriesListFragment;
import com.example.maris.vehiclemanager.Fragments.DateFilterFragment;
import com.example.maris.vehiclemanager.Fragments.ExpensesListFragment;
import com.example.maris.vehiclemanager.Fragments.HomeFragment;
import com.example.maris.vehiclemanager.Fragments.VehiclesListFragment;
import com.example.maris.vehiclemanager.Model.AppViewModel;
import com.example.maris.vehiclemanager.Model.Database.Category;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.Date;

import static android.view.animation.AnimationUtils.loadAnimation;

public class MainActivityMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        DateFilterFragment.OnFragmentInteractionListener,
        ExpensesListFragment.OnExpensesListFragmentInteractionListener,
        VehiclesListFragment.OnVehiclesListFragmentInteractionListener,
        CategoriesListFragment.OnCategoriesListFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener
{

    //private FABToolbarLayout morph;
    private HomeFragment homeFragment;

    Spinner spinner;

    private AppViewModel viewModel;
    private Dialog editDialog;
    private Category editingCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(MainActivityMenu.this, MainActivityLogin.class));

        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        editDialog = new Dialog(this);
        editDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editDialog.setContentView(R.layout.dialog_edit_category);
        Window dialogWindow = editDialog.getWindow();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button saveDialogBtn = editDialog.findViewById(R.id.dialog_category_btn_save);
        EditText nameDialogEdit = editDialog.findViewById(R.id.dialog_category_edit_name);
        saveDialogBtn.setOnClickListener(view -> {
            if (nameDialogEdit.getText().toString().isEmpty()) {
                //TODO: move to resources
                Toast.makeText(this, "Not saved, empty category name.", Toast.LENGTH_LONG).show();
            }

            Category category = editingCategory;
            if (category == null) {
                category = new Category(0, nameDialogEdit.getText().toString(), "");
            }
            category.setCategory(nameDialogEdit.getText().toString());
            viewModel.insertOrUpdateCategories(category).subscribe();
            editDialog.dismiss();
        });

        if(savedInstanceState == null) {
            homeFragment = new HomeFragment();
        }
        else {
            homeFragment = (HomeFragment)getSupportFragmentManager().getFragments().get(0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        //View toolbar_img_refuel, toolbar_img_service, toolbar_img_expense, toolbar_img_reminder;
        /*
        toolbar_img_refuel = findViewById(R.id.toolbar_img_refuel);
        toolbar_img_service = findViewById(R.id.toolbar_img_service);
        toolbar_img_reminder = findViewById(R.id.toolbar_img_reminder);
        toolbar_img_expense = findViewById(R.id.toolbar_img_expense);*/
        //fab.setOnClickListener(this);
        /*toolbar_img_refuel.setOnClickListener(this);
        toolbar_img_service.setOnClickListener(this);
        toolbar_img_expense.setOnClickListener(this);
        toolbar_img_reminder.setOnClickListener(this);*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.content,homeFragment).commit();

    }

   @Override
   public void onClick(View v) {
       /* if (v.getId() == R.id.fab) {
            morph.show();
        }
        morph.hide();*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        android.support.v4.app.Fragment miFragment = null;
        boolean fragmentSeleccionado=false;

        int id = item.getItemId();

        //TODO: create methods to get non duplicated fragments and save them on variables
        if (id == R.id.nav_home) {
            miFragment = homeFragment;
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_categories) {
            miFragment = new CategoriesListFragment();
            fragmentSeleccionado=true;
        } else if (id == R.id.nav_cars) {
            miFragment = new VehiclesListFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_new_expense) {
            Intent intent = new Intent(this,EditAddExpenses.class);
            startActivity(intent);
        } else if (id == R.id.nav_new_category) {
            editingCategory = null;
            editDialog.show();
        } else if (id == R.id.nav_new_car) {
            Intent intent = new Intent(this,EditorAddVehicle.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(this,AboutUs.class);
            startActivity(intent);
        }

        if(fragmentSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.content,miFragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTypeChanged(String dateType) {
        homeFragment.setDateType(dateType);
    }

    @Override
    public void onDateChanged(Date date) {
        homeFragment.setSelectedDate(date);
    }

}
