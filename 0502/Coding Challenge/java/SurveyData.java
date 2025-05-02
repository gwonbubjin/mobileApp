package com.example.survey;

import java.util.ArrayList;

public class SurveyData {
    public static String[] questions = {
            "오늘 아침을 드셨나요?",
            "하루 8시간 이상 수면을 취했나요?",
            "하루 물 섭취량은 1리터 이상인가요?",
            "하루 30분 이상 걷거나 운동하셨나요?",
            "과일 또는 채소를 하루 2번 이상 섭취하셨나요?",
            "오늘 스마트폰 사용 시간이 3시간 이하인가요?",
            "스트레칭이나 휴식을 1번 이상 취했나요?",
            "카페인 음료를 2잔 이하로 마셨나요?",
            "정해진 시간에 식사를 했나요?",
            "하루 동안 30분 이상 집중해서 무언가를 해보았나요?"
    };

    public static ArrayList<Boolean> answers = new ArrayList<>();
}