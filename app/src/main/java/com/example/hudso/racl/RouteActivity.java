package com.example.hudso.racl;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.example.hudso.racl.outro.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RouteActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Mandracarias do WebService
        GetJson download = new GetJson();
        //Chama Async Task
        download.execute();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        Log.d("Hudson", id + " - " + item.getTitle());
//        Toast.makeText(this.getBaseContext(), id + " - " + item.getTitle(), Toast.LENGTH_LONG);
//
//        //noinspection SimplifiableIfStatement
//        /**if (id == R.id.action_settings) {
//            return true;
//        }*/
//
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView;
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            if (section == 1) {
                rootView = inflater.inflate(R.layout.fragment_detail, container, false);

                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                carregaCampinhoPorFavor = textView;
                textView.setText(resultadoParaEuUsar);

            } else {
                rootView = inflater.inflate(R.layout.fragment_route, container, false);
            }

            return rootView;
        }
    }

 private static TextView carregaCampinhoPorFavor = null;

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Detalhe";
                case 1:
                    return "Mapa";
            }
            return null;
        }
    }

    private ProgressDialog load;
    private static String resultadoParaEuUsar = "Não carregou";


    private class GetJson extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute(){
            load = ProgressDialog.show(RouteActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected String doInBackground(Void... params) {
//            return new Utils().getInformacao("https://randomuser.me/api/0.7");
//            return new Utils().getInformacao("https://drive.google.com/file/d/0B-4YWQESpZpsSWVVSnMxaExpUmM/view?usp=sharing");
            return new Utils().getInformacao("https://drive.google.com/uc?id=0B-4YWQESpZpsZWw1eVhEMnVoZjg&export=download");
        }

        @Override
        protected void onPostExecute(String veio){
            resultadoParaEuUsar = veio;

            if (carregaCampinhoPorFavor != null) {
                carregaCampinhoPorFavor.setText(resultadoParaEuUsar);
            }

            load.dismiss();
        }
    }
//
//    private PessoaObj parseJson(String json){
//        try {
//            PessoaObj pessoa = new PessoaObj();
//
//            JSONObject jsonObj = new JSONObject(json);
//            JSONArray array = jsonObj.getJSONArray("results");
//
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Date data;
//
//            JSONObject objArray = array.getJSONObject(0);
//
//            JSONObject obj = objArray.getJSONObject("user");
//            //Atribui os objetos que estão nas camadas mais altas
////            pessoa.setEmail(obj.getString("email"));
//
//            return pessoa;
//        }catch (JSONException e){
//            e.printStackTrace();
//            return null;
//        }
//    }
}
