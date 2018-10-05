package thread;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Task - Реализовать следующий интерфейс:
 * void getOccurencies(String[] sources, String[] words, String res) throws …;
 *
 * Многоточие означает необходимые для реализации исключения
 *
 * Функциональные требования: метод получает на вход массив адресов
 * ресурсов (файлы, FTP, web-ссылки) и слов. Необходимо в
 * многопоточном режиме найти вхождения всех слов второго массива в
 * ресурсы. Если слово входит в предложение, это предложение
 * добавляется в файл по адресу res. При начале исполнения метода
 * файл очищается (чтобы исключить наличие старой информации).
 *
 * Все ресурсы текстовые. Необходимо определить оптимальную производительность.
 * Возможны ситуации как с большим числом ресурсов (>2000 текстовых ресурсов
 * каждый <2кб), так и с очень большими ресурсами (ресурс>1ГБ).
 * Максимальный размер массива ресурсов 2000 элементов.
 * Максимальный размер массива слов 2000 элементов. Предложение это
 * последовательность слов, начинающаяся с  заглавной буквы и заканчивающаяся
 * точкой, вопросительным знаком, восклицательным знаком,
 * или многоточием. Внутри предложения могут находиться знаки
 * препинания. Слово это последовательность символов кириллических,
 * либо латинских.
 */

/**
 * Interface Occurencies (more in detail to the task description)
 */
public interface Occurencies {
    void getOccurancies(String[] sources, String[] words, String res) throws Exception;
}

/**
 * Class realization in task up
 */
class ClOccurancies implements Occurencies{

    private List<String> resList = new ArrayList<>();
    private List<Thread> threads = new ArrayList<>();

    /**
     * Write something text in the adjusting file and in the adjusting directory
     * @param sources source of input files
     * @param words search for a given word
     * @param res saving file directory with file name
     * @exception InterruptedException
     */
    public void getOccurancies(String[] sources, String[] words, String res){

        ThreadPool pool = new ThreadPool(2);
        for (String source: sources) {
            ParserOneFile parser = new ParserOneFile(resList, words, source);
            threads.add(pool.run(parser));
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writeFile(resList,res);
    }

    /**
     * Write something text in the adjusting file and in the adjusting directory
     * @param in checking word
     * @param res saving file directory with file name
     * @exception FileNotFoundException
     * @exception IOException
     */
    private void  writeFile (List<String> in, String res){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(res))) {
            for (String s : in) {
                writer.write(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}