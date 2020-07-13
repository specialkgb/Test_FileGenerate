package com.biz.score.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.biz.score.domain.ScoreVO;

public class ScoreServiceImplV1 implements ScoreService {

	protected List<ScoreVO> scoreList;
	protected Scanner scan;
	private String fileName;
	private int[] totalSum;
	private int[] totalAvg;

	public ScoreServiceImplV1() {
		scoreList = new ArrayList<ScoreVO>();
		fileName = "src/com/biz/score/data/score.txt";
		scan = new Scanner(System.in);
		totalSum = new int[5];
	}

	@Override
	public void loadScore() {

		FileReader fileReader = null;
		BufferedReader buffer = null;
		fileName = "src/com/biz/score/data/score.txt";

		try {
			fileReader = new FileReader(this.fileName);
			buffer = new BufferedReader(fileReader);
			String reader = "";

			while (true) {
				reader = buffer.readLine();
				if (reader == null) {
					break;
				}

				String[] scores = reader.split(":");

				ScoreVO scoreVO = new ScoreVO();
				scoreVO.setNum(scores[0]);
				scoreVO.setKor(Integer.valueOf(scores[1]));
				scoreVO.setEng(Integer.valueOf(scores[2]));
				scoreVO.setMath(Integer.valueOf(scores[3]));
				scoreVO.setMusic(Integer.valueOf(scores[4]));

				scoreList.add(scoreVO);
			}
			buffer.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean inputScore() {
		scan = new Scanner(System.in);

		System.out.print("학번 입력(end:종료) >> ");
		String strNum = scan.nextLine();

		int intNum = 0;
		if (strNum == null || strNum.equalsIgnoreCase("end")) {
			return false;
		}
		try {
			intNum = Integer.valueOf(strNum);
		} catch (NumberFormatException exception) {
			System.out.println("학번은 정수만 입력하세요!");
			return true;
		}
		strNum = String.format("%05d", intNum);
		for (ScoreVO sVO : scoreList) {
			if (sVO.getNum().equals(strNum)) {
				System.out.println(strNum + " 학번이 이미 등록되어 있어 입력할 수 없습니다");
				return true;
			}
		}
		System.out.println("국어 점수를 입력하세요 >> ");
		String strKor = scan.nextLine();
		int intKor = 0;
		try {
			intKor = Integer.valueOf(strKor);
		} catch (Exception e) {
			System.out.println("숫자만 입력 가능");
		}
		if (intKor < 0 || intKor > 100) {
			System.out.println("점수는 0 ~ 100까지만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}

		System.out.println("영어 점수를 입력하세요 >> ");
		String strEng = scan.nextLine();
		int intEng = 0;
		try {
			intEng = Integer.valueOf(strEng);
		} catch (Exception e) {
			System.out.println("숫자만 입력 가능");
		}
		if (intEng < 0 || intEng > 100) {
			System.out.println("점수는 0 ~ 100까지만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}

		int intMath = 0;
		System.out.println("수학 점수를 입력하세요>> ");
		String strMath = scan.nextLine();
		try {
			intMath = Integer.valueOf(strMath);
		} catch (Exception e) {
			System.out.println("숫자만 입력 가능");
		}
		if (intMath < 0 || intMath > 100) {
			System.out.println("점수는 0 ~ 100까지만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}

		int intMusic = 0;
		System.out.println("음악 점수를 입력하세요>> ");
		String strMusic = scan.nextLine();
		try {
			intMusic = Integer.valueOf(strMusic);
		} catch (Exception e) {
			System.out.println("숫자만 입력 가능");
		}
		if (intMath < 0 || intMath > 100) {
			System.out.println("점수는 0 ~ 100까지만 가능");
			System.out.println("다시 입력해 주세요");
			return true;
		}

		ScoreVO scoreVO = new ScoreVO();
		scoreVO.setNum(strNum);
		scoreVO.setKor(intKor);
		scoreVO.setEng(intEng);
		scoreVO.setMath(intMath);
		scoreVO.setMusic(intMusic);

		scoreList.add(scoreVO);
		this.calcSum();
		this.calcAvg();
		this.saveScore(scoreVO);

		return true;
	}

	@Override
	public void calcSum() {
		for (ScoreVO scoreVO : scoreList) {
			int sum = scoreVO.getKor();
			sum += scoreVO.getEng();
			sum += scoreVO.getMath();
			sum += scoreVO.getMusic();
			scoreVO.setSum(sum);
		}
	}

	@Override
	public void calcAvg() {
		for (ScoreVO scoreVO : scoreList) {
			int sum = scoreVO.getSum();
			float avg = (float) sum / 4;
			scoreVO.setAvg(avg);

		}
	}

	@Override
	public void scoreList() {
		ScoreVO scoreVO;
		System.out.println("========================================================");
		System.out.println("성적일람표");
		System.out.println("========================================================");
		System.out.println("학번\t국어\t영어\t수학\t음악\t총점\t평균");
		System.out.println("--------------------------------------------------------");

		int size = scoreList.size();

		for (int i = 0; i < size; i++) {
			scoreVO = scoreList.get(i);
			System.out.print(scoreVO.getNum() + "\t");
			System.out.print(scoreVO.getKor() + "\t");
			System.out.print(scoreVO.getEng() + "\t");
			System.out.print(scoreVO.getMath() + "\t");
			System.out.print(scoreVO.getMusic() + "\t");
			System.out.print(scoreVO.getSum() + "\t");
			System.out.printf("%5.2f\t\n", scoreVO.getAvg());

			totalSum[0] += scoreVO.getKor();
			totalSum[1] += scoreVO.getEng();
			totalSum[2] += scoreVO.getMath();
			totalSum[3] += scoreVO.getMusic();

		}
		System.out.println("--------------------------------------------------------");
		System.out.print("과목총점");
		int subSum = 0;

		subSum = totalSum[0] + totalSum[1] + totalSum[2] + totalSum[3];
		System.out.printf("\t%s\t%s\t%s\t%s\t%s\t%s\n", totalSum[0], totalSum[1], totalSum[2], totalSum[3], 0, subSum);

		System.out.print("과목평균");

		System.out.printf("\t%.0f\t%.0f\t%.0f\t%.0f\t%d\t%.2f\n", ((float) totalSum[0] / scoreList.size()),
				((float) totalSum[1] / scoreList.size()), ((float) totalSum[2] / scoreList.size()),
				((float) totalSum[3] / scoreList.size()), 0, ((float) subSum / scoreList.size()));

		System.out.println("========================================================");

	}

	@Override
	public void saveScore(ScoreVO scoreVO) {

		FileWriter fileWriter = null;
		PrintWriter pWriter = null;

		try {
			fileWriter = new FileWriter(this.fileName, true);
			pWriter = new PrintWriter(fileWriter);

			pWriter.printf("%s:", scoreVO.getNum());
			pWriter.printf("%d:", scoreVO.getKor());
			pWriter.printf("%d:", scoreVO.getEng());
			pWriter.printf("%d:", scoreVO.getMath());
			pWriter.printf("%d\n", scoreVO.getMusic());
			pWriter.flush();
			pWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void scoreListSave() {
		PrintStream pStream = null;

		String saveFile = "src/com/biz/score/data/scoreList.txt";

		try {
			pStream = new PrintStream(saveFile);
			ScoreVO scoreVO;
			pStream.println("========================================================");
			pStream.println("성적일람표");
			pStream.println("========================================================");
			pStream.println("학번\t\t국어\t영어\t수학\t음악\t총점\t평균");
			pStream.println("--------------------------------------------------------");

			int size = scoreList.size();

			for (int i = 0; i < size; i++) {
				scoreVO = scoreList.get(i);
				pStream.print(scoreVO.getNum() + "\t");
				pStream.print(scoreVO.getKor() + "\t");
				pStream.print(scoreVO.getEng() + "\t");
				pStream.print(scoreVO.getMath() + "\t");
				pStream.print(scoreVO.getMusic() + "\t");
				pStream.print(scoreVO.getSum() + "\t");
				pStream.printf("%5.2f\t\n", scoreVO.getAvg());

				totalSum[0] += scoreVO.getKor();
				totalSum[1] += scoreVO.getEng();
				totalSum[2] += scoreVO.getMath();
				totalSum[3] += scoreVO.getMusic();

			}
			pStream.println("--------------------------------------------------------");
			pStream.print("과목총점");
			int subSum = 0;

			subSum = totalSum[0] + totalSum[1] + totalSum[2] + totalSum[3];
			pStream.printf("\t%s\t%s\t%s\t%s\t%s\t%s\n", totalSum[0], totalSum[1], totalSum[2], totalSum[3], 0, subSum);

			pStream.print("과목평균");

			pStream.printf("\t%.0f\t%.0f\t%.0f\t%.0f\t%d\t%.2f\n", ((float) totalSum[0] / scoreList.size()),
					((float) totalSum[1] / scoreList.size()), ((float) totalSum[2] / scoreList.size()),
					((float) totalSum[3] / scoreList.size()), 0, ((float) subSum / scoreList.size()));

			pStream.println("========================================================");
		} catch (FileNotFoundException e ) {
			System.out.println("파일을 찾을 수 없습니다.");
		}
	}
}