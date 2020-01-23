package my.tech.mysubmission4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import my.tech.mysubmission4.fragment.NavigationFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = new NavigationFragment();
            addFragment(fragment, R.id.contentFrame);
        }
    }

    private void addFragment(Fragment fragment, int contentFrame) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(contentFrame, fragment);
        ft.commit();
    }
}
