package com.example.yoo.Registeration;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends BaseAdapter {

    private Context context;
    private List<Course>  courseList;
    private Fragment parentFrag;
    private  String userID = MainActivity.userID;
    private  Schedule schedule = new Schedule();
    private List<Integer> courseIDList;
    public static int totalCredit = 0;

    public CourseListAdapter(Context context, List<Course> noticeList, Fragment parent) {
        this.context = context;
        this.courseList = noticeList;
        this.parentFrag = parent;
        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();
        new BackgroudTask().execute();
        totalCredit = 0;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View v = View.inflate(context, R.layout.course, null);
        TextView courseGrade = (TextView) v.findViewById(R.id.courseGrade);
        TextView courseTitle = (TextView) v.findViewById(R.id.courseTitle);
        TextView courseCredit = (TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide = (TextView) v.findViewById(R.id.courseDivide);
        TextView courseProfessor = (TextView) v.findViewById(R.id.courseProfessor);
        TextView coursePersonnel = (TextView) v.findViewById(R.id.coursePersonnel);
        TextView courseTime = (TextView) v.findViewById(R.id.courseTime);

        if (courseList.get(position).getCourseGrade().equals("no limit") || courseList.get(position).getCourseGrade().equals("")) {
            courseGrade.setText("all grade");
        } else {
            courseGrade.setText(courseList.get(position).getCourseGrade() + "Grade");
        }

        courseTitle.setText(courseList.get(position).getCourseTitle());
        courseCredit.setText(courseList.get(position).getCourseCredit() + "Credit");
        courseDivide.setText(courseList.get(position).getCourseDivid() + "Divide");

        if (courseList.get(position).getCourseProfessor().equals("")) {
            courseProfessor.setText("Personal");
        } else {
            courseProfessor.setText(courseList.get(position).getCourseProfessor());
        }

        if (courseList.get(position).getCourseGrade().equals("no limit") || courseList.get(position).getCourseGrade().equals("")) {
            courseGrade.setText("no limit");
        } else {
            coursePersonnel.setText(courseList.get(position).getCoursePersonnel() + "Grade");
        }

        courseTime.setText(courseList.get(position).getCourseTime());

        v.setTag(courseList.get(position).getCourseID());

        Button addButton = (Button) v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validate = false;
                validate = schedule.validate(courseList.get(position).getCourseTime());

                if (!aleadyIn(courseIDList, courseList.get(position).getCourseID())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parentFrag.getActivity());
                    AlertDialog dialog = builder.setMessage("aleady exist.")
                            .setPositiveButton("retry", null)
                            .create();
                    dialog.show();
                } else if (totalCredit + courseList.get(position).getCourseCredit() > 24) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parentFrag.getActivity());
                    AlertDialog dialog = builder.setMessage("check the CourseCredit(OVER 24).")
                            .setPositiveButton("retry", null)
                            .create();
                    dialog.show();
                } else if (validate == false) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(parentFrag.getActivity());
                    AlertDialog dialog = builder.setMessage("check the CourseTime.")
                            .setPositiveButton("retry", null)
                            .create();
                    dialog.show();
                } else {
                    String userID = MainActivity.userID;
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonRespose = new JSONObject(response);
                                boolean success = jsonRespose.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parentFrag.getActivity());
                                    AlertDialog dialog = builder.setMessage("Add success")
                                            .setPositiveButton("OK", null)
                                            .create();
                                    dialog.show();
                                    courseIDList.add(courseList.get(position).getCourseID());
                                    schedule.addSchedule(courseList.get(position).getCourseTime());
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(parentFrag.getActivity());
                                    AlertDialog dialog = builder.setMessage("Add failed")
                                            .setNegativeButton("OK", null)
                                            .create();
                                    dialog.show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    AddRequest addRequest = new AddRequest(userID, courseList.get(position).getCourseID(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parentFrag.getActivity());
                    queue.add(addRequest);
                }

            }
        });
        return v;
    }

    class BackgroudTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://bcb9c240.ngrok.io/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseProfessor, courseTime;
                int courseID;
                totalCredit = 0;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseTime = object.getString("courseTime");
                    courseProfessor = object.getString("courseProfessor");
                    totalCredit += object.getInt("courseCredit");
                    courseIDList.add(courseID);
                    schedule.addSchedule(courseTime);
                    count++;
                }

                //ReloadData
                //noticeListView.invalidateViews();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public boolean aleadyIn(List<Integer> courseIDList, int item) {
        for (int i=0; i < courseIDList.size(); i++) {
            if (courseIDList.get(i) == item) {
                return false;
            }
        }
        return true;
    }
}
