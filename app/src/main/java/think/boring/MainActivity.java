package think.boring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mzule.activityrouter.annotation.Router;

import think.common.router.RouterList;

@Router(RouterList.Main.MAIN)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
