//Smart Solutionss
package pingpong.app.machine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.machine.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    String timePeriod;
    private FirebaseAuth mAuth;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.statistics_fragment, container, false);
        final TextView avgSpeedText = v.findViewById(R.id.avgSpeed);
        final TextView avgSpeedValText = v.findViewById(R.id.avgSpeedVal);

        final TextView minSpeedText = v.findViewById(R.id.minSpeed);
        final TextView minSpeedValText = v.findViewById(R.id.minSpeedVal);

        final TextView maxSpeedText = v.findViewById(R.id.maxSpeed);
        final TextView maxSpeedValText = v.findViewById(R.id.maxSpeedVal);

        final TextView accuracyText = v.findViewById(R.id.accuracy);
        final TextView accuracyValText = v.findViewById(R.id.accuracyVal);

        final TextView shotsTotalText = v.findViewById(R.id.shotTotal);
        final TextView ballShotValText = v.findViewById(R.id.ballShotVal);

        final TextView shotsHitText = v.findViewById(R.id.shotsHit);
        final TextView ballHitValText = v.findViewById(R.id.ballHitVal);

        final TextView shotsMissedText = v.findViewById(R.id.shotsMissed);
        final TextView ballMissValText = v.findViewById(R.id.ballMissVal);

        List<String> mSpinner = new ArrayList<String>();

        mSpinner.add(getActivity().getApplicationContext().getString(R.string.selectTime));
        mSpinner.add(getActivity().getApplicationContext().getString(R.string.daily)); //We would be pulling data from our server instead of hard coding the months in.
        mSpinner.add(getActivity().getApplicationContext().getString(R.string.monthly));
        mSpinner.add(getActivity().getApplicationContext().getString(R.string.yearly));
        mSpinner.add(getActivity().getApplicationContext().getString(R.string.alltime));

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mSpinner);

        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner userHistory = v.findViewById(R.id.spinner);
        userHistory.setAdapter(mAdapter);

        userHistory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int num, long id) {
                timePeriod = adapterView.getItemAtPosition(num).toString();
                if (!(timePeriod.equalsIgnoreCase(getActivity().getApplicationContext().getString(R.string.selectTime)))) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String avgSpeedUser = "Users/" + user.getUid() + "/Average Speed: ";
                    String minSpeedUser = "Users/" + user.getUid() + "/Minimum Speed: ";
                    String maxSpeedUser = "Users/" + user.getUid() + "/Maximum Speed ";
                    String accuracyUser = "Users/" + user.getUid() + "/Accuracy ";
                    String totalShotsUser = "Users/" + user.getUid() + "/Total Shots Fired: ";
                    String totalHitUser = "Users/" + user.getUid() + "/Total Balls Hit: ";
                    String totalMissedUser = "Users/" + user.getUid() + "/Total Balls Missed: ";

                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    DatabaseReference avgSpeedVal = database.getReference(avgSpeedUser);
                    DatabaseReference minSpeedVal = database.getReference(minSpeedUser);
                    DatabaseReference maxSpeedVal = database.getReference(maxSpeedUser);
                    DatabaseReference accVal = database.getReference(accuracyUser);
                    DatabaseReference totalShotsVal = database.getReference(totalShotsUser);
                    DatabaseReference totalHitVal = database.getReference(totalHitUser);
                    DatabaseReference totalMissedVal = database.getReference(totalMissedUser);

                    avgSpeedVal.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            avgSpeedValText.setText(value);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                    minSpeedVal.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            minSpeedValText.setText(value);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                    maxSpeedVal.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            maxSpeedValText.setText(value);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });

                    accVal.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            accuracyValText.setText(value);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });

                    totalShotsVal.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            ballShotValText.setText(value);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });

                    totalHitVal.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            ballHitValText.setText(value);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                    totalMissedVal.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value = dataSnapshot.getValue(String.class);
                            ballMissValText.setText(value);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });

                    avgSpeedText.setVisibility(View.VISIBLE);
                    avgSpeedValText.setVisibility(View.VISIBLE);
                    minSpeedText.setVisibility(View.VISIBLE);
                    minSpeedValText.setVisibility(View.VISIBLE);
                    maxSpeedText.setVisibility(View.VISIBLE);
                    maxSpeedValText.setVisibility(View.VISIBLE);
                    accuracyText.setVisibility(View.VISIBLE);
                    accuracyValText.setVisibility(View.VISIBLE);
                    shotsTotalText.setVisibility(View.VISIBLE);
                    ballShotValText.setVisibility(View.VISIBLE);
                    shotsHitText.setVisibility(View.VISIBLE);
                    ballHitValText.setVisibility(View.VISIBLE);
                    shotsMissedText.setVisibility(View.VISIBLE);
                    ballMissValText.setVisibility(View.VISIBLE);
                    userData(timePeriod);
                } else {
                    avgSpeedText.setVisibility(View.INVISIBLE);
                    avgSpeedValText.setVisibility(View.INVISIBLE);
                    minSpeedText.setVisibility(View.INVISIBLE);
                    minSpeedValText.setVisibility(View.INVISIBLE);
                    maxSpeedText.setVisibility(View.INVISIBLE);
                    maxSpeedValText.setVisibility(View.INVISIBLE);
                    accuracyText.setVisibility(View.INVISIBLE);
                    accuracyValText.setVisibility(View.INVISIBLE);
                    shotsTotalText.setVisibility(View.INVISIBLE);
                    ballShotValText.setVisibility(View.INVISIBLE);
                    shotsHitText.setVisibility(View.INVISIBLE);
                    ballHitValText.setVisibility(View.INVISIBLE);
                    shotsMissedText.setVisibility(View.INVISIBLE);
                    ballMissValText.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        return v;
    }

    public void userData(String userTime) {
        final String dailyStringResource = getResources().getString(R.string.daily);
        final String monthlyStringResource = getResources().getString(R.string.monthly);
        final String yearlyStringResource = getResources().getString(R.string.yearly);
        final String allTimeStringResource = getResources().getString(R.string.alltime);
        if (userTime.equalsIgnoreCase((dailyStringResource))) {//This is how we'll pull data for a specific month for our user
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.toastDaily), Toast.LENGTH_LONG).show();
        } else if (userTime.equalsIgnoreCase((monthlyStringResource))) {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.toastMonthly), Toast.LENGTH_LONG).show();
        } else if (userTime.equalsIgnoreCase((yearlyStringResource))) {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.toastYearly), Toast.LENGTH_LONG).show();
        } else if (userTime.equalsIgnoreCase((allTimeStringResource))) {
            Toast.makeText(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.toastAlltime), Toast.LENGTH_LONG).show();
        }
    }

}

