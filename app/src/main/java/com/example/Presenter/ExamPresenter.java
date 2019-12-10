package com.example.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.ExamActivity;
import com.example.Activity.MyAnswerActivity;
import com.example.Constant;
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

    public ExamPresenter(Context context, ExamActivity examActivity) {
        this.examActivity = examActivity;
        this.context = context;
        this.examModel = new ExamModel(context, 0);
    }

    public void record() {
        examModel.myAnswerRecord(examActivity.radioA, examActivity.radioB, examActivity.radioC, examActivity.radioD);
    }

    public void forwordBtn() {
/*
        if (examActivity.isHandIn) {// 交卷后
            int tindex = examModel.curIndex;
            while (--tindex >= 0) {
                if (examModel.mySelect[tindex] != examModel.testAnswer[tindex])//显示错题
                {
                    examModel.curIndex = tindex;//交卷后为第一题
                    examModel.OnPaint(textView_pro, examActivity.rightAnswer, examActivity.myAnswer, radioGroup, radio_a, radio_b, radio_c, radio_d);
                    return;
                }
            }
            //ShowToast("当前为第一题");
            return;
        } else {*/
            examModel.curIndex--;//不是第一页且没有交卷返回上一题
            if (examModel.curIndex <= examModel.numberOfChosen - 1) {
                examActivity.next_btn.setText("下一题");
            }
            if (examModel.curIndex == 0) {
                examActivity.forword_btn.setText("无");
            }
            if (examModel.curIndex < 0) {
                examModel.curIndex = 0;
            }
            examTestShow();
            //examModel.OnPaint(textView_pro, examActivity.rightAnswer, examActivity.myAnswer, radioGroup, radio_a, radio_b, radio_c, radio_d);
            String temp = (examModel.curIndex + 1) + "/" + examModel.numberOfChosen;
            examActivity.numText.setText(temp);
        }
    //}

    public void nextBtn() {
/*        if (examActivity.isHandIn) {
            int tindex = examModel.curIndex;
            while (++tindex < examModel.numberOfChosen) {
                if (examModel.mySelect[tindex] != examModel.testAnswer[tindex]) {
                    examModel.curIndex = tindex;
                    examModel.OnPaint(textView_pro, examActivity.rightAnswer, examActivity.myAnswer, radioGroup, radio_a, radio_b, radio_c, radio_d);
                    return;
                }
            }
            //ShowToast("当前为最后一题");再点击的话跳到答题卡页面
            return;
        } else {*/
            examModel.curIndex++;
            if (examModel.curIndex >= 1)
                examActivity.forword_btn.setText("上一题");

            if (examModel.curIndex == examModel.numberOfChosen - 1) {
                examActivity.next_btn.setText("提交");
            }
            if (examModel.curIndex == 15) {
                Intent intent = new Intent(examActivity, MyAnswerActivity.class);
                intent.putExtra("answer", examModel.mySelect);
                intent.putExtra("test_answer", examModel.testAnswer);
                int temp = examActivity.timeTrans();
                intent.putExtra("timer", temp);
                Log.i(TAG, "LRL temp  is =============================" + temp);
                examActivity.startActivityForResult(intent, 1);
                examModel.curIndex = 14;
            }
            if (examActivity.isHandIn && examModel.curIndex == 14) {
                examActivity.forword_btn.setEnabled(false);
                examActivity.next_btn.setText("无");
            } else examActivity.next_btn.setEnabled(true);
            examTestShow();
            //Log.i(TAG,"LRL curIndex now is ======"+curIndex);
            //examModel.OnPaint(textView_pro, examActivity.rightAnswer, examActivity.myAnswer, radioGroup, radio_a, radio_b, radio_c, radio_d);//直接下一题
            String temp = (examModel.curIndex + 1) + "/" + examModel.numberOfChosen;
            examActivity.numText.setText(temp);
        //}
    }

    public void answerBtn() {                // TODO Auto-generated method stub
        Intent intent = new Intent(examActivity, MyAnswerActivity.class);
        intent.putExtra("answer", examModel.mySelect);
        intent.putExtra("test_answer", examModel.testAnswer);
        int temp = examActivity.timeTrans();
        intent.putExtra("timer", temp);
        examActivity.startActivityForResult(intent, 1);
    }

    public void handleOfAnswer(int curIndex) {
        String temp = null;
        if (curIndex <= 4) {
            if (examModel.mySelect[curIndex] == 1) {
                temp = "对";
                examActivity.myAnswer.setText("您的答案" + temp);
            } else if (examModel.mySelect[curIndex] == 3) {
                temp = "错";
                examActivity.myAnswer.setText("您的答案" + temp);
            } else if (examModel.mySelect[curIndex] == 0) {
                temp = "未选";
                examActivity.myAnswer.setText("您的答案：" + temp);
            }
        } else if (curIndex > 4 && curIndex <= 14) {
            if (examModel.mySelect[curIndex] == 1) {
                temp = "A";
                examActivity.myAnswer.setText("您的答案" + temp);
            } else if (examModel.mySelect[curIndex] == 2) {
                temp = "B";
                examActivity.myAnswer.setText("您的答案" + temp);
            } else if (examModel.mySelect[curIndex] == 3) {
                temp = "C";
                examActivity.myAnswer.setText("您的答案" + temp);
            } else if (examModel.mySelect[curIndex] == 4) {
                temp = "D";
                examActivity.myAnswer.setText("您的答案" + temp);
            }
        }
    }
    public void examTestShow()
    {
        if (!examActivity.isHandIn)
        {
            proTextShow();
        }
        else
        {
            proTextShow();
            handInAnswerShow();
        }
    }
    public void handInAnswerShow()
    {
        handleOfAnswer(examModel.curIndex);
        examActivity.rightAnswer.setText("正确答案："+examModel.testAnswer[examModel.curIndex]);
    }
    public void proTextShow()
    {
        examModel.getProTxt(examActivity.radioGroup);
        examActivity.proTextView.setText((examModel.curIndex + 1) + "." + examModel.TESTSUBJECT);
        if (examModel.ANSWERA.compareTo("") == 0) {
            // 判断题
            examActivity.radioA.setText("对");
            examActivity.radioC.setText("错");
            examActivity.radioB.setVisibility(View.GONE);
            examActivity.radioD.setVisibility(View.GONE);
        } else {
            // 选择题
            examActivity.radioA.setText("A." + examModel.ANSWERA);
            examActivity.radioB.setText("B." + examModel.ANSWERB);
            examActivity.radioC.setText("C." + examModel.ANSWERC);
            examActivity.radioD.setText("D." + examModel.ANSWERD);
            examActivity.radioA.setVisibility(View.VISIBLE);
            examActivity.radioB.setVisibility(View.VISIBLE);
            examActivity.radioC.setVisibility(View.VISIBLE);
            examActivity.radioD.setVisibility(View.VISIBLE);
        }
        switch (examModel.mySelect[examModel.curIndex]) {
            case 1:
                examActivity.radioA.setChecked(true);
                break;
            case 2:
                examActivity.radioB.setChecked(true);
                break;
            case 3:
                examActivity.radioC.setChecked(true);
                break;
            case 4:
                examActivity.radioD.setChecked(true);
                break;
            default:
                break;
        }
    }
    public void paint(TextView proTextView, TextView rightAnswer,TextView myAnswer,RadioGroup radioGroup, RadioButton radio_a, RadioButton radio_b, RadioButton radio_c, RadioButton radio_d)
    {
        //examModel.OnPaint(proTextView,examActivity.rightAnswer,examActivity.myAnswer,radioGroup,radio_a,radio_b,radio_c,radio_d);//直接下一题
    }

    public void setIndex(int index)
    {
        examModel.curIndex = index;
    }
    public String testMode(int flag) {
        String mode = null;
        switch (flag) {
            case 1:
            {mode = Constant.CHAPTER;}
            break;
            case 2:
            {mode = Constant.RANDOM;}
            break;
            case 3:
            {mode = Constant.ERROR;}
            break;
            case 4:
            {mode = Constant.TEST;}
            break;
            default:
                break;
        }
        return mode;
    }
}
