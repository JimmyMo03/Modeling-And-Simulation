import random
import matplotlib.pyplot as plt
import PySimpleGUI as sg

type = 'F'
demand = 0

numberOfNewspapers = [40, 60, 80, 100, 120]  # ARRAY OF ALL POSSIBILITIES


# FUNCTION TO ASSIGN VALUES ACCORDING TO RANDOM ASSIGNED VARIABLE
def compute_values():
    global type, demand
    if typeRandomNumber <= 18:
        type = 'E'
        if demandRandomNumber <= 7:
            demand = 50
        elif demandRandomNumber <= 15:
            demand = 60
        elif demandRandomNumber <= 27:
            demand = 70
        elif demandRandomNumber <= 40:
            demand = 80
        elif demandRandomNumber <= 62:
            demand = 90
        elif demandRandomNumber <= 85:
            demand = 100
        elif demandRandomNumber <= 93:
            demand = 110
        else:
            demand = 120

    elif typeRandomNumber <= 60:
        type = 'G'
        if demandRandomNumber <= 6:
            demand = 40
        elif demandRandomNumber <= 15:
            demand = 50
        elif demandRandomNumber <= 31:
            demand = 60
        elif demandRandomNumber <= 50:
            demand = 70
        elif demandRandomNumber <= 78:
            demand = 80
        elif demandRandomNumber <= 90:
            demand = 90
        elif demandRandomNumber <= 97:
            demand = 100
        else:
            demand = 110

    elif typeRandomNumber <= 92:
        type = 'F'
        if demandRandomNumber <= 15:
            demand = 40
        elif demandRandomNumber <= 37:
            demand = 50
        elif demandRandomNumber <= 65:
            demand = 60
        elif demandRandomNumber <= 83:
            demand = 70
        elif demandRandomNumber <= 93:
            demand = 80
        elif demandRandomNumber <= 98:
            demand = 90
        else:
            demand = 100

    else:
        type = 'P'
        if demandRandomNumber <= 42:
            demand = 40
        elif demandRandomNumber <= 70:
            demand = 50
        elif demandRandomNumber <= 84:
            demand = 60
        elif demandRandomNumber <= 94:
            demand = 70
        elif demandRandomNumber <= 99:
            demand = 80
        else:
            demand = 90


# FUNCTION TO PRINT ALL DATA
def print_data():
    print("{:<8} {:<10} {:<8} {:<10} {:<8} {:<8} {:<8} {:<8} {:<8}".format(j + 1, typeRandomNumber, type,
                                                                           demandRandomNumber, demand,
                                                                           round(revenue, 3),
                                                                           round(lostMoney, 3), round(scrapRevenue, 3),
                                                                           round(profit, 3)))


# FUNCTION TO PRINT TOTAL PROFIT FOR EACH POSSIBILITY AND EXTRA STATS
def print_total_profit(r, l, s, d):
    print('\n')
    print(f'1) THE TOTAL PROFIT FOR BUYING {bought} NEWSPAPERS = {round(totalProfit, 3)}')

    # EXTRA STATS
    print(f'2) THE AVERAGE DEMAND FOR BUYING {bought} NEWSPAPERS = {round(d / 20, 3)}')
    print(f'3) THE AVERAGE REVENUE FOR BUYING {bought} NEWSPAPERS = {round(r / 20, 3)}')
    print(f'4) THE AVERAGE LOST MONEY BECAUSE OF EXCESS DEMAND FOR BUYING {bought} NEWSPAPERS = {round(l / 20, 3)}')
    print(f'5) THE AVERAGE SCRAP REVENUE FOR BUYING {bought} NEWSPAPERS = {round(s / 20, 3)}')


# PRINT TABLE FOR EACH POSSIBILITY
def print_new_table():
    print("----------------------")
    print('\n')
    print(f'THIS IS THE CASE OF BUYING {bought} NEWSPAPERS:')
    print('\n')
    print("{:<8} {:<10} {:<8} {:<10} {:<8} {:<8} {:<8} {:<8} {:<8}".format('#', 'R type', 'Type', 'R Demand', 'Demand',
                                                                           'Revenue', 'Lost', 'Scrap', 'Profit'))


# PRINT AVERAGE TOTAL PROFIT
def print_average_total_profit(avg):
    print('\n')
    print(f'AVERAGE TOTAL PROFIT OVER 50 RUNS FOR {bought} NEWSPAPERS = {round(avg, 3)}')


# DETERMINE THE BEST CHOICE AMONG ALL POSSIBILITIES BY PERFORMING SEVERAL RUNS
def determine_optimal():
    global bought, totals, profit, lostMoney, lostInExcess, scrap, totalProfit, revenue, cost, scrapRevenue, j, typeRandomNumber, demandRandomNumber
    for bought in numberOfNewspapers:
        average_total_profit = 0
        totals = 0
        profits = []
        for x in range(0, 50):
            profit = 0
            lostMoney = 0
            lostInExcess = 0
            scrap = 0
            totalProfit = 0
            revenue = 0
            cost = 0
            scrapRevenue = 0

            for j in range(0, 20):
                profit = 0
                lostMoney = 0
                lostInExcess = 0
                scrap = 0
                revenue = 0
                cost = 0
                scrapRevenue = 0

                typeRandomNumber = random.randint(0, 100)
                demandRandomNumber = random.randint(0, 100)

                compute_values()

                if (demand - bought) > 0:
                    lostInExcess = demand - bought
                    scrap = 0
                    revenue = bought * 0.7

                elif (demand - bought) < 0:
                    lostInExcess = 0
                    scrap = bought - demand
                    revenue = demand * 0.7

                else:
                    revenue = demand * 0.7
                    lostInExcess = 0
                    scrap = 0

                cost = bought * 0.5
                scrapRevenue = scrap * 0.15
                lostMoney = lostInExcess * 0.7

                profit += revenue - cost - lostMoney + scrapRevenue
                totalProfit += profit
                totals += totalProfit

            profits.append(round(totalProfit, 1))

        average_total_profit = totals / 50
        if bought == 40:
            global forty
            forty = average_total_profit
        if bought == 60:
            global sixty
            sixty = average_total_profit
        if bought == 80:
            global eighty
            eighty = average_total_profit
        if bought == 100:
            global hundred
            hundred = average_total_profit
        if bought == 120:
            global one_twenty
            one_twenty = average_total_profit

        print_average_total_profit(average_total_profit)
        plot_histo(bought, profits)


# PLOT HISTOGRAM
def plot_histo(b, p):
    plt.hist(p)
    plt.title(f'The distribution of {b} Newspapers')
    plt.show()


# PERFORM ONE RUN OF 20 DAYS FOR EACH POSSIBILITY
def normal_simulation():
    global bought, totalProfit, j, profit, lostMoney, lostInExcess, scrap, revenue, cost, scrapRevenue, typeRandomNumber, demandRandomNumber
    for bought in numberOfNewspapers:
        print_new_table()

        totalProfit = 0
        total_revenue = 0
        total_lost_in_excess = 0
        total_scrap = 0
        total_demand = 0

        for j in range(0, 20):
            profit = 0
            lostMoney = 0
            lostInExcess = 0
            scrap = 0
            revenue = 0
            cost = 0
            scrapRevenue = 0

            global typeRandomNumber
            global demandRandomNumber

            typeRandomNumber = random.randint(0, 100)  # GENERATE RANDOM NUMBER
            demandRandomNumber = random.randint(0, 100)

            compute_values()

            if (demand - bought) > 0:
                lostInExcess = demand - bought
                scrap = 0
                revenue = bought * 0.7

            elif (demand - bought) < 0:
                lostInExcess = 0
                scrap = bought - demand
                revenue = demand * 0.7

            else:
                revenue = demand * 0.7
                lostInExcess = 0
                scrap = 0

            cost = bought * 0.5
            scrapRevenue = scrap * 0.15
            lostMoney = lostInExcess * 0.7

            profit += revenue - cost - lostMoney + scrapRevenue
            totalProfit += profit

            total_revenue += revenue  # EXTRA STATS
            total_lost_in_excess += lostMoney
            total_scrap += scrapRevenue
            total_demand += demand

            print_data()

        print_total_profit(total_revenue, total_lost_in_excess, total_scrap, total_demand)


# GUI
layout = [[sg.Text('press to start normal simulation:'), sg.Button('Normal')],
          [sg.Text('press to compare avg total profits:'), sg.Button('Optimal')],
          [sg.Button('Cancel')]]

window = sg.Window('Window Title', layout)
while True:
    event1, event2 = window.read()
    if event1 == sg.WIN_CLOSED or event1 == 'Cancel':  # if user closes window or clicks cancel
        break
    if event1 == 'Normal':
        normal_simulation()
    if event1 == 'Optimal':
        determine_optimal()

window.close()
