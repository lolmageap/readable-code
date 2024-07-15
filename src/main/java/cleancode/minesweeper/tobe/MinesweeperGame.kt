package cleancode.minesweeper.tobe

import java.util.*

fun main() {
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    println("지뢰찾기 게임 시작!")
    println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
    val scanner = Scanner(System.`in`)
    for (i in 0..7) {
        for (j in 0..9) {
            board[i][j] = "□"
        }
    }
    for (i in 0..9) {
        val col = Random().nextInt(10)
        val row = Random().nextInt(8)
        landMines[row][col] = true
    }
    for (i in 0..7) {
        for (j in 0..9) {
            var count = 0
            if (!landMines[i][j]) {
                if (i - 1 >= 0 && j - 1 >= 0 && landMines[i - 1][j - 1]) {
                    count++
                }
                if (i - 1 >= 0 && landMines[i - 1][j]) {
                    count++
                }
                if (i - 1 >= 0 && j + 1 < 10 && landMines[i - 1][j + 1]) {
                    count++
                }
                if (j - 1 >= 0 && landMines[i][j - 1]) {
                    count++
                }
                if (j + 1 < 10 && landMines[i][j + 1]) {
                    count++
                }
                if (i + 1 < 8 && j - 1 >= 0 && landMines[i + 1][j - 1]) {
                    count++
                }
                if (i + 1 < 8 && landMines[i + 1][j]) {
                    count++
                }
                if (i + 1 < 8 && j + 1 < 10 && landMines[i + 1][j + 1]) {
                    count++
                }
                landMineCounts[i][j] = count
                continue
            }
            landMineCounts[i][j] = 0
        }
    }
    while (true) {
        println("   a b c d e f g h i j")
        for (i in 0..7) {
            System.out.printf("%d  ", i + 1)
            for (j in 0..9) {
                print(board[i][j] + " ")
            }
            println()
        }
        if (gameStatus == 1) {
            println("지뢰를 모두 찾았습니다. GAME CLEAR!")
            break
        }
        if (gameStatus == -1) {
            println("지뢰를 밟았습니다. GAME OVER!")
            break
        }
        println()
        println("선택할 좌표를 입력하세요. (예: a1)")
        val input = scanner.nextLine()
        println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)")
        val input2 = scanner.nextLine()
        val c = input[0]
        val r = input[1]
        var col: Int
        col = when (c) {
            'a' -> 0
            'b' -> 1
            'c' -> 2
            'd' -> 3
            'e' -> 4
            'f' -> 5
            'g' -> 6
            'h' -> 7
            'i' -> 8
            'j' -> 9
            else -> -1
        }
        val row = Character.getNumericValue(r) - 1
        if (input2 == "2") {
            board[row][col] = "⚑"
            var open = true
            for (i in 0..7) {
                for (j in 0..9) {
                    if (board[i][j] == "□") {
                        open = false
                    }
                }
            }
            if (open) {
                gameStatus = 1
            }
        } else if (input2 == "1") {
            if (landMines[row][col]) {
                board[row][col] = "☼"
                gameStatus = -1
                continue
            } else {
                open(row, col)
            }
            var open = true
            for (i in 0..7) {
                for (j in 0..9) {
                    if (board[i][j] == "□") {
                        open = false
                    }
                }
            }
            if (open) {
                gameStatus = 1
            }
        } else {
            println("잘못된 번호를 선택하셨습니다.")
        }
    }
}

private val board = Array<Array<String?>>(8) {
    arrayOfNulls(
        10
    )
}
private val landMineCounts = Array<Array<Int?>>(8) {
    arrayOfNulls(
        10
    )
}
private val landMines = Array(8) {
    BooleanArray(
        10
    )
}
private var gameStatus = 0 // 0: 게임 중, 1: 승리, -1: 패배

private fun open(row: Int, col: Int) {
    if (row < 0 || row >= 8 || col < 0 || col >= 10) {
        return
    }
    if (board[row][col] != "□") {
        return
    }
    if (landMines[row][col]) {
        return
    }
    if (landMineCounts[row][col] != 0) {
        board[row][col] = landMineCounts[row][col].toString()
        return
    } else {
        board[row][col] = "■"
    }
    open(row - 1, col - 1)
    open(row - 1, col)
    open(row - 1, col + 1)
    open(row, col - 1)
    open(row, col + 1)
    open(row + 1, col - 1)
    open(row + 1, col)
    open(row + 1, col + 1)
}