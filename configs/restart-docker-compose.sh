for i in {0..5} ; do
  rm -rf ./800$i/data;
done

docker-compose up -d