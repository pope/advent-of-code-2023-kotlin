package com.shifteleven.adventofcode2023.day01

import kotlin.math.min

fun main() {
	part1()
	part2()
}

fun part1() {
	val lines = object {}.javaClass.getResourceAsStream("/input.txt")!!.bufferedReader().readLines()
	val sum =
		lines
			.stream()
			.mapToInt({ line ->
				var tens: Int? = null
				var ones = 0
				for (c in line) {
					when (c) {
						in CharRange('0', '9') -> {
							ones = c.code - 0x30
							tens = tens ?: ones * 10
						}
					}
				}
				tens!! + ones
			})
			.sum()
	println("Part 1: " + sum)
}

fun part2() {
	val lines = object {}.javaClass.getResourceAsStream("/input.txt")!!.bufferedReader().readLines()
	val sum =
		lines
			.stream()
			.mapToInt({ line ->
				var tens: Int? = null
				var ones = 0
				for ((i, c) in line.withIndex()) {
					var num: Int? = null
					when (c) {
						in CharRange('0', '9') -> {
							num = c.code - 0x30
						}
						'o' -> {
							if (matchesWord(line, i, "one")) {
								num = 1
							}
						}
						't' -> {
							if (matchesWord(line, i, "two")) {
								num = 2
							} else if (matchesWord(line, i, "three")) {
								num = 3
							}
						}
						'f' -> {
							if (matchesWord(line, i, "four")) {
								num = 4
							} else if (matchesWord(line, i, "five")) {
								num = 5
							}
						}
						's' -> {
							if (matchesWord(line, i, "six")) {
								num = 6
							} else if (matchesWord(line, i, "seven")) {
								num = 7
							}
						}
						'e' -> {
							if (matchesWord(line, i, "eight")) {
								num = 8
							}
						}
						'n' -> {
							if (matchesWord(line, i, "nine")) {
								num = 9
							}
						}
					}
					if (num != null) {
						ones = num
						tens = tens ?: num * 10
					}
				}
				tens!! + ones
			})
			.sum()
	println("Part 2: " + sum)
}

fun matchesWord(
	line: String,
	idx: Int,
	word: String,
): Boolean {
	return line.subSequence(idx, min(idx + word.length, line.length)) == word
}
