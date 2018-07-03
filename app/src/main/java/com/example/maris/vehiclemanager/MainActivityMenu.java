package com.example.maris.vehiclemanager;

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

import com.example.maris.vehiclemanager.Fragments.DateFilterFragment;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

public class MainActivityMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        DateFilterFragment.OnFragmentInteractionListener{

    private FABToolbarLayout morph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);

        View toolbar_img_refuel, toolbar_img_service, toolbar_img_expense, toolbar_img_reminder;

        toolbar_img_refuel = findViewById(R.id.toolbar_img_refuel);
        toolbar_img_service = findViewById(R.id.toolbar_img_service);
        toolbar_img_reminder = findViewById(R.id.toolbar_img_reminder);
        toolbar_img_expense = findViewById(R.id.toolbar_img_expense);
        fab.setOnClickListener(this);
        toolbar_img_refuel.setOnClickListener(this);
        toolbar_img_service.setOnClickListener(this);
        toolbar_img_expense.setOnClickListener(this);
        toolbar_img_reminder.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            morph.show();
        }

        morph.hide();
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

        if (id == R.id.nav_home) {
            fragmentSeleccionado = true;
        }  else if (id == R.id.nav_expenses) {
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_noti) {
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_categories) {
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_cars) {
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_setting) {
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_about) {
            fragmentSeleccionado = true;
        }

        if(fragmentSeleccionado){
            //getSupportFragmentManager().beginTransaction().replace(R.id.content,miFragment).commit();
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
}
