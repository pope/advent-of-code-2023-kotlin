package com.shifteleven.adventofcode2023.day02

import java.io.StreamTokenizer
import java.io.StringReader
import kotlin.assert
import kotlin.math.max

data class Game(val id: Int, val views: List<IntArray>)

fun main() {
	val lines = object {}.javaClass.getResourceAsStream("/input.txt")!!.bufferedReader().readLines()
	val games = lines.map { processLine(it) }
	part1(games)
	part2(games)
}

fun part1(games: List<Game>) {
	val sum =
		games
			.filter { game -> game.views.all { it[0] <= 12 && it[1] <= 13 && it[2] <= 14 } }
			.map { it.id }
			.sum()
	println("Part 1: " + sum)
}

fun part2(games: List<Game>) {
	val sum =
		games
			.map { game ->
				var r = 1
				var g = 1
				var b = 1
				for (dice in game.views) {
					r = max(dice[0], r)
					g = max(dice[1], g)
					b = max(dice[2], b)
				}
				r * g * b
			}
			.sum()
	println("Part 2: " + sum)
}

fun processLine(line: String): Game {
	val r = StringReader(line)
	val t = StreamTokenizer(r)

	t.nextToken()
	assert(t.sval == "Game")

	t.nextToken()
	assert(t.ttype == StreamTokenizer.TT_NUMBER)
	val gameId = t.nval.toInt()

	val cur = t.nextToken() // :
	assert(cur.toChar() == ':')

	val views = mutableListOf<IntArray>()

	do {
		val dice = IntArray(3)
		do {
			t.nextToken()
			assert(t.ttype == StreamTokenizer.TT_NUMBER)
			val curColor = t.nval.toInt()
			t.nextToken()
			assert(t.ttype == StreamTokenizer.TT_WORD)
			when (t.sval) {
				"red" -> dice[0] = curColor
				"green" -> dice[1] = curColor
				"blue" -> dice[2] = curColor
				else -> assert(false)
			}
		} while (isRgbGroupActive(t))

		views.add(dice)
	} while (isGameContinued(t))

	assert(t.nextToken() == StreamTokenizer.TT_EOF)

	return Game(gameId, views)
}

fun isRgbGroupActive(t: StreamTokenizer): Boolean {
	val tok = t.nextToken()
	if (tok.toChar() == ',') {
		return true
	}
	t.pushBack()
	return false
}

fun isGameContinued(t: StreamTokenizer): Boolean {
	val tok = t.nextToken()
	if (tok.toChar() == ';') {
		return true
	}
	t.pushBack()
	return false
}
