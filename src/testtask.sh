#!/bin/bash
app_name='testtask.jar'
start() {
  java -jar $app_name &
  if [ $? -eq 0 ]; then
    echo -e "Started"
  else
    echo -e "Error"
  fi
}
stop() {
  ps ux | grep $app_name | grep -v grep | awk '{print $2}' | xargs kill
  if [ $? -eq 0 ]; then
    echo -e "Terminated"
  else
    echo -e "Error"
  fi
}
help() {
  echo "Usage: $0 start|stop|restart"
}
case "$2" in
start)
  start
  ;;
stop)
  stop
  ;;
restart)
  stop
  start
  ;;
*)
  help
  exit 1
  ;;
esac
