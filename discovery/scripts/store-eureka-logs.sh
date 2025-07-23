#!/bin/bash
LOG_DIR="/logs"
ARCHIVE_DIR="/logs/archive"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)

mkdir -p $ARCHIVE_DIR
tar -czf $ARCHIVE_DIR/eureka-logs-$TIMESTAMP.tar.gz $LOG_DIR/*.log
find $LOG_DIR -type f -name "*.log" -delete