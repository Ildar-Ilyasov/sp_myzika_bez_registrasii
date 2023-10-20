## Музыкальный загрузчик

### Описание Проекта
Этот проект представляет собой программу для загрузки файлов с веб-страниц через указанные URL, которые вы можете задать во входном текстовом файле. Программа также отслеживает ход загрузки, что может быть полезно, если вы хотите отслеживать ход загрузки больших файлов.

### Описание Кода
Ядро программы – это класс `Main`, который содержит следующие основные компоненты:

1. **Параметры путей к файлам:**
    * `IN_FILE_TXT` - входной текстовый файл с URL-адресами
    * `OUT_FILE_TXT` - выходной текстовый файл, который будет создан после обработки
    * `PATH_TO_MUSIC` - каталог, где будут храниться музыкальные файлы.

2. **Шаблон извлечения URL (`URL_PATTERN`):**
Регулярное выражение, используемое для извлечения URL-адресов из HTML-файлов.

3. **Главный метод:**
Здесь URL-адреса считываются из входного файла, обрабатываются и загружается их контент. Метод управляет потенциальными `IOExceptions` и контролирует исполнение загрузки.

4. **Вспомогательные методы:**
   * `writeMusicLinksFromUrl` - извлекает URL-адреса из указанных адресов, для загрузки файлов, управляет потенциальными `IOExceptions`.
   * `downloadUsingHttpURLConn` - загружает контент файла по его URL и сохраняет его под указанным именем файла, обрабатывает потенциальные `IOExceptions`.

### Как Использовать
1. Заполните входной текстовый файл (`IN_FILE_TXT`) URL-адресами, с которых вы хотите загрузить музыку.
2. Откорректируйте пути к файлам (`IN_FILE_TXT`, `OUT_FILE_TXT`, `PATH_TO_MUSIC`) и возможно лимит загрузки (`i`) в методе `writeMusicLinksFromUrl`, чтобы он соответствовал вашим потребностям.
3. Компилируйте и запускайте главный класс.

Убедитесь, что предоставлены необходимые разрешения на создание, чтение и запись файлов на указанных путях.

### Примечания
Пожалуйста, учтите, что использование этой программы должно соответствовать условиям использования веб-сайтов, с которых вы загружаете контент. Нарушение авторских прав может быть наказуемым по закону. Используйте на свой страх и риск.
