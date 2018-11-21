#!/usr/bin/env bash

for (( i = 1; i <= $1; i++ ))
do
	  if [ $i = 1 ]; then
		touch -t 1105041352.00 "Banque du Canada.qif"
	elif [ $i = 2 ]; then
		touch -t 1105041430.34 "Narodowy Bank Polski.ofx"
        elif [ $i = 3 ]; then
                touch -t 1105041445.57 "Nordea Bank AB.qfx"
        elif [ $i = 4 ]; then
                touch -t 1105050111.43 "HSBC.ofc"
        elif [ $i = 5 ]; then
                touch -t 1105050749.31 "Bank of America.csv"
        else
		DATE="$((RANDOM%2+10))$((RANDOM%18+10))$((RANDOM%14+10))$((RANDOM%50+10))"
		touch -t $DATE "file$i.qif"
	fi
done
echo "Created $1 QIF files"
