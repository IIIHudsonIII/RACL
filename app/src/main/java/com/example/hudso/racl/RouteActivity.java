package com.example.hudso.racl;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.hudso.racl.bean.PointBean;
import com.example.hudso.racl.bean.RouteBean;
import com.example.hudso.racl.bean.ScheduleBean;
import com.example.hudso.racl.outro.SingletonTeste;
import com.example.hudso.racl.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.example.hudso.racl.outro.Metodos.getInstance;

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

                TextView textView = (TextView) rootView.findViewById(R.id.tw_detail_route_name);
                carregaCampinhoPorFavor = textView;
                textView.setText(resultadoParaEuUsar);

            } else {
                rootView = inflater.inflate(R.layout.fragment_route, container, false);
            }

            return rootView;
        }
    }

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
    private static TextView carregaCampinhoPorFavor = null;


    private class GetJson extends AsyncTask<Void, Void, RouteBean> {

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(RouteActivity.this, "Por favor Aguarde ...", "Recuperando Informações do Servidor...");
        }

        @Override
        protected RouteBean doInBackground(Void... params) {
//            return new Utils().getInfoRoute("https://randomuser.me/api/0.7");
            //return new Utils().getInfoRoute("https://drive.google.com/uc?id=0B-4YWQESpZpsVFA3UkMtU1JJUlE&export=download");
            return new Utils().getInfoRoute("http://10.0.2.2:8080/route");
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(RouteBean route) {
            SingletonTeste.getInstance().setRoute(route);

            Fragment fragment = RouteActivity.this.getSupportFragmentManager().getFragments().get(0);
            if (fragment != null) {
                TextView textView = fragment.getActivity().findViewById(R.id.tw_detail_route_name);
                if (textView != null) {
                    textView.setText(route.getName());
                }

                LinearLayout vScroll = fragment.getActivity().findViewById(R.id.scrollPoints);
                HorizontalScrollView hScroll;

                for (PointBean point : route.getPoints()) {
                    hScroll = new HorizontalScrollView(vScroll.getContext());

                    TextView information = new TextView(getBaseContext());
                    information.setText(" - "+point.getName());
                    hScroll.addView(information);

                    vScroll.addView(hScroll);
                }

                vScroll = fragment.getActivity().findViewById(R.id.scrollSchedules);

                for (ScheduleBean schedule : route.getSchedules()) {
                    hScroll = new HorizontalScrollView(vScroll.getContext());

                    TextView information1 = new TextView(getBaseContext());
                    information1.setTextSize(14);
                    information1.setText(" - "+schedule.getFormattedSchedule());
                    hScroll.addView(information1);

                    vScroll.addView(hScroll);
                }

            }

            fragment = RouteActivity.this.getSupportFragmentManager().getFragments().get(1);
            if (fragment != null) {
                getInstance().drawDynamicRoute(route);

                TextView textView = fragment.getActivity().findViewById(R.id.tw_internal_map);
                if (textView != null) {
                    textView.setText("Rota: " + route.getName());
                }
            }
            load.dismiss();
        }
    }
}