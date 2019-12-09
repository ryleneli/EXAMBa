package com.example.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.ExamActivity;
import com.example.Activity.MyAnswerActivity;
import com.example.Model.ExamModel;

/**
 * com.example.Presenter
 * Created by Ryleneli 15151
 * 2019/12/8
 */
public class ExamPresenter {
    private static String TAG = "ExamPresenter";
    private Context context;
    private ExamActivity examActivity;
    private ExamModel examModel;

    public ExamPresenter(Context context, ExamActivity examActivity){
        this.examActivity=examActivity;
        this.context=context;
        this.examModel = new ExamModel(context,0);
    }
    public void record(RadioButton radio_a,
                  RadioButton radio_b, RadioButton radio_c, RadioButton radio_d){
        examModel.myAnswerRecord(radio_a,radio_b,radio_c,radio_d);
    }
    public void forwordBtn (Button button_for, Button button_nex, TextView textView_num, TextView textView_pro, RadioGroup radioGroup, RadioButton radio_a, RadioButton radio_b, RadioButton radio_c, RadioButton radio_d) {

        if (examActivity.isHandIn) {// 交卷后
            int tindex = examModel.curIndex;
            while (--tindex >= 0) {
                if (examModel.mySelect[tindex] != examModel.testAnswer[tindex])//显示错题
                {
                    examModel.curIndex = tindex;//交卷后为第一题
                    examModel.OnPaint(textView_pro,examActivity.rightAnswer,examActivity.myAnswer,radioGroup,radio_a,radio_b,radio_c,radio_d);
                    return;
                }
            }
            //ShowToast("当前为第一题");
            return;
        } else {
            examModel.curIndex--;//不是第一页且没有交卷返回上一题
            if (examModel.curIndex <= examModel.numberOfChosen - 1) {
                button_nex.setText("下一题");
            }
            if (examModel.curIndex == 0) {
                button_for.setText("无");
            }
            if (examModel.curIndex < 0) {
                //Toast.makeText(mcontext, "默认的Toast", Toast.LENGTH_SHORT).show();
                examModel.curIndex = 0;
            }

            examModel.OnPaint(textView_pro,examActivity.rightAnswer,examActivity.myAnswer,radioGroup,radio_a,radio_b,radio_c,radio_d);
            String temp = (examModel.curIndex + 1) + "/" + examModel.numberOfChosen;
            textView_num.setText(temp);
        }
    }
    public void nextBtn(Activity activity, boolean handIn, Button button_for, Button button_nex, TextView textView_num, TextView textView_pro, RadioGroup radioGroup, RadioButton radio_a, RadioButton radio_b, RadioButton radio_c, RadioButton radio_d)
    {
        if (examActivity.isHandIn) {
            int tindex = examModel.curIndex;
            while (++tindex < examModel.numberOfChosen) {
                if (examModel.mySelect[tindex] != examModel.testAnswer[tindex]) {
                    examModel.curIndex = tindex;
                    examModel.OnPaint(textView_pro,examActivity.rightAnswer,examActivity.myAnswer,radioGroup,radio_a,radio_b,radio_c,radio_d);
                    return;
                }
            }
            //ShowToast("当前为最后一题");再点击的话跳到答题卡页面
            return;
        } else {
            examModel.curIndex++;
            if (examModel.curIndex >= 1)
                button_for.setText("上一题");

            if (examModel.curIndex == examModel.numberOfChosen - 1) {
                button_nex.setText("提交");
            }
            if (examModel.curIndex == 15)
            {   Intent intent = new Intent(activity, MyAnswerActivity.class);
                intent.putExtra("answer",examModel.mySelect);
                intent.putExtra("test_answer",examModel.testAnswer);
                int temp = examActivity.timeTrans();
                intent.putExtra("timer",temp);
                Log.i(TAG,"LRL temp  is ============================="+temp );
                activity.startActivityForResult(intent,1);
                examModel.curIndex = 14;
            }
            if (handIn && examModel.curIndex == 14)
            {
                button_nex.setEnabled(false);
                button_nex.setText("无");
            }
            else button_nex.setEnabled(true);
            //Log.i(TAG,"LRL curIndex now is ======"+curIndex);
            examModel.OnPaint(textView_pro,examActivity.rightAnswer,examActivity.myAnswer,radioGroup,radio_a,radio_b,radio_c,radio_d);//直接下一题
            String temp = (examModel.curIndex + 1) + "/" + examModel.numberOfChosen;
            textView_num.setText(temp);
        }
    }
}
