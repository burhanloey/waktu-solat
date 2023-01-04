from operator import itemgetter
import time
import requests
import sys


if (len(sys.argv) == 1) or (not sys.argv[1].isdigit()):
    sys.exit('Input argument for the year, for e.g: python download.py 2023')


url_template = 'https://www.e-solat.gov.my/index.php?r=esolatApi/takwimsolat&period=year&zone={district_code}'
file_path_template = '{year}/{{district_code}}.json'.format(year = sys.argv[1])


states = [
        {'code': 'JHR', 'district_count': 4},
        {'code': 'KDH', 'district_count': 7},
        {'code': 'KTN', 'district_count': 2},
        {'code': 'MLK', 'district_count': 1},
        {'code': 'NGS', 'district_count': 3},
        {'code': 'PHG', 'district_count': 6},
        {'code': 'PLS', 'district_count': 1},
        {'code': 'PNG', 'district_count': 1},
        {'code': 'PRK', 'district_count': 7},
        {'code': 'SBH', 'district_count': 9},
        {'code': 'SGR', 'district_count': 3},
        {'code': 'SWK', 'district_count': 9},
        {'code': 'TRG', 'district_count': 4},
        {'code': 'WLY', 'district_count': 2}
    ]


for state in states:
    state_code, district_count = itemgetter('code', 'district_count')(state)

    for district_number in range(1, district_count + 1):
        time.sleep(5)

        district_code = '{}0{}'.format(state_code, district_number)
        url = url_template.format(district_code = district_code)
        file_path = file_path_template.format(district_code = district_code)

        response = requests.get(url, timeout = 5)
        if response:
            with open(file_path, 'w') as file:
                file.write(response.text)
            print('Success getting url: {}'.format(url))
        else:
            print('FAILED getting url: {}'.format(url))
