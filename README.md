���������: Swing
���� ������: MS SQL Server 2014
�������������� � ��: JDBC
������: Maven
IDE: Eclipse 

��� ��������� - ������ ������ ������� �� ������������ ������� �������.
�������� �������� ������� � ���� ������ MS SQL Server 2014.

������� � ���� ������ MS SQL Server (sqljdbc4.jar) �������� � uber-jar sqlmeter-1.jar. ������ ����������� ���������� �� ��������������. � ����� � ����������� ������ sqlmeter-1.jar ������ ���������� ���������������� ���� config.ini. � config.ini ������������� ������ � ���� ������. ��� ������������� ��� ��������� ����� ���� ��������.

���������� �������� � ����� ��������� (������� ������ � ������� ����������), ������� ����������� � ���� ��������. ������ � �������� ������������� � ������� ������ ��������, ��������, �������.

������� ���������� ����� �������������� ������ ���������, ��� ������� �� ������� ����������� ������ (������ � ��). � �������� ���������� ���� ���� ���������, ������� �� ������ ������ ����� ������������ ��� �������� ����������, ���������� � ������� XML:

1) ��������� ����������� � ��, ����:
<connection host="localhost" port="1433" dbname="schedule" username="user" pass="12345"> </connection>
���� � ���������� ������� ��������� �����������, �� ���������� � �� ����� ��������������� ��������� ��� ���������. ���� ���� �������� ����������� ���������� ���������� �� ���������� �� ����� config.ini.

2)���������� �������� ����������, ����:
<orderby column="schd_ID"></orderby>
� ���� ��������� ����� ������� ���� �� �������� ����� ����������� ���������� �� �����������. � SQL ������� ����� ���������� ORDER BY � ��������������� �����.

����� ������� ������ � ���� ������������ ��������� ������. � ����� ���������� �������, � ���� "����� ����������" ������������ ����� �� ������� ��� ������� �����.

����� ���� ��������� � ����� schedule.bak


