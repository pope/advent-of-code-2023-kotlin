package com.shifteleven.adventofcode2023.day01

fun main() {
	val lines = object {}.javaClass.getResourceAsStream("/input.txt")!!.bufferedReader().readLines()
	val sum =
		lines
			.parallelStream()
			// .stream()
			.mapToInt({ line ->
				var tens: Int? = null
				var ones = 0
				for (c in line) {
					when (c) {
						in CharRange('0', '9') -> {
							tens = tens ?: (c.code - 0x30) * 10
							ones = c.code - 0x30
						}
					}
				}
				tens!! + ones
			})
			.sum()
	println(sum)
}
