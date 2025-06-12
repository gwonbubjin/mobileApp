package com.example.pilly;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BottomNavHelper {

    // ✅ 탭 활성화 색상 적용 함수
    public static void setNavActive(Activity activity, int activeNavId) {
        int[] navIds = {
                R.id.nav_record,
                R.id.nav_today,
                R.id.nav_challenge,
                R.id.nav_care,
                R.id.nav_profile
        };
        for (int id : navIds) {
            LinearLayout nav = activity.findViewById(id);
            if (nav != null && nav.getChildCount() == 2) {
                ImageView icon = (ImageView) nav.getChildAt(0);
                TextView text = (TextView) nav.getChildAt(1);
                if (id == activeNavId) {
                    icon.setColorFilter(Color.parseColor("#6BC48F")); // 활성
                    text.setTextColor(Color.parseColor("#6BC48F"));
                } else {
                    icon.setColorFilter(Color.parseColor("#CCCCCC")); // 비활성
                    text.setTextColor(Color.parseColor("#888888"));
                }
            }
        }
    }

    // ✅ 각 탭 클릭 이벤트 처리
    public static void bind(final Activity activity) {

        // 기록 탭
        if (activity.findViewById(R.id.nav_record) != null) {
            activity.findViewById(R.id.nav_record).setOnClickListener(v -> {
                if (activity instanceof RecordActivity) {
                    ((RecordActivity) activity).refreshPage();
                    Toast.makeText(activity, "기록 새로고침!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(activity, RecordActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }

        // 오늘 탭
        if (activity.findViewById(R.id.nav_today) != null) {
            activity.findViewById(R.id.nav_today).setOnClickListener(v -> {
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).refreshPage();
                    Toast.makeText(activity, "오늘 새로고침!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    activity.finish();
                }
            });
        }

        // 챌린지 탭
        if (activity.findViewById(R.id.nav_challenge) != null) {
            activity.findViewById(R.id.nav_challenge).setOnClickListener(v -> {
                if (!(activity instanceof ChallengeActivity)) {
                    Intent intent = new Intent(activity, ChallengeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Toast.makeText(activity, "챌린지 새로고침!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // 돌봄 탭
        if (activity.findViewById(R.id.nav_care) != null) {
            activity.findViewById(R.id.nav_care).setOnClickListener(v -> {
                if (activity instanceof CareActivity) {
                    Toast.makeText(activity, "돌봄 새로고침!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(activity, CareActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }

        // ✅ 내정보 탭
        if (activity.findViewById(R.id.nav_profile) != null) {
            activity.findViewById(R.id.nav_profile).setOnClickListener(v -> {
                if (!(activity instanceof ProfileActivity)) {
                    Intent intent = new Intent(activity, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Toast.makeText(activity, "내정보 새로고침!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // ✅ 현재 활성화된 탭 색상 적용
        if (activity instanceof RecordActivity)
            setNavActive(activity, R.id.nav_record);
        else if (activity instanceof MainActivity)
            setNavActive(activity, R.id.nav_today);
        else if (activity instanceof ChallengeActivity)
            setNavActive(activity, R.id.nav_challenge);
        else if (activity instanceof CareActivity)
            setNavActive(activity, R.id.nav_care);
        else if (activity instanceof ProfileActivity)
            setNavActive(activity, R.id.nav_profile); // ✅ 새로 추가됨
    }
}
