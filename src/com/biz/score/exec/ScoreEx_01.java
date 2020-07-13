package com.biz.score.exec;

import java.util.Scanner;

import com.biz.score.service.ScoreService;
import com.biz.score.service.ScoreServiceImplV1;

public class ScoreEx_01 {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		ScoreService scService = new ScoreServiceImplV1();
		scService.loadScore();

		while (true) {
			System.out.println("==================================");
			System.out.println("성적기록부");
			System.out.println("==================================");
			System.out.println("1. 성적 입력");
			System.out.println("2. 성적표 출력");
			System.out.println("3. 성적표 파일로 저장");
			System.out.println("4. 프로그램 종료");
			System.out.println("==================================");
			System.out.print("메뉴선택 >> ");

			String strMenu = scan.nextLine();
			
			int intMenu = 0;
			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("메뉴선택은 숫자로만 입력하세요");
				continue;
			}
			
			if (intMenu == 4) {
				System.out.println("시스템이 종료됩니다.");
				break;
				
			} else if (intMenu < 1 || intMenu > 3) {
				System.out.println("1 ~ 3번 메뉴 중에 선택해주세요");
				continue;
			}

			if (intMenu == 1) {
				scService.inputScore();


			} else if (intMenu == 2) {
				scService.scoreList();
				
				
			} else if (intMenu == 3) {
				scService.scoreListSave();
			}
		}
	}
}
